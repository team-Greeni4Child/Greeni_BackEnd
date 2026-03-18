package com.greeni.api.ai.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.greeni.api.ai.domain.Purpose;
import com.greeni.api.ai.dto.AIRequestDTO;
import com.greeni.api.ai.dto.AIResponseDTO;
import com.greeni.api.apiPayload.handler.GeneralException;
import com.greeni.api.apiPayload.status.CommonErrorStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AIService {

	private final WebClient aiWebClient;

	public Mono<AIResponseDTO.FiveQuestionsHintResponse> fiveQuestionsHint(AIRequestDTO.FiveQuestionsHint request) {

		Purpose purpose = Purpose.GAME;

		return aiWebClient.post()
			.uri("/game/fiveq/hint")
			.bodyValue(request)
			.retrieve()
			.bodyToMono(AIResponseDTO.FiveQuestionsHintText.class)

			.flatMap(context -> {
				List<String> hintList = context.hints();
				if (hintList == null) {
					hintList = Collections.emptyList();
				}

				if (hintList.isEmpty()) {
					return Mono.just(AIResponseDTO.FiveQuestionsHintResponse.builder()
						.hints(Collections.emptyList())
						.build());
				}
				return Flux.fromIterable(hintList)
					.concatMap(hintText -> {
						AIRequestDTO.TTSRequest ttsRequest = new AIRequestDTO.TTSRequest(
							purpose, hintText, null, null, null
						);
						return aiWebClient.post()
							.uri("/tts/speak")
							.bodyValue(ttsRequest)
							.retrieve()
							.bodyToMono(byte[].class)
							.doOnError(WebClientResponseException.class, e -> {
								log.error("TTS 에러: {}", e.getResponseBodyAsString());
							})
							.map(audioBytes -> {
								String base64Audio = Base64.getEncoder().encodeToString(audioBytes);
								return new AIResponseDTO.HintData(hintText, base64Audio);
							});
					})
					.collectList()
					.map(hintDataList -> AIResponseDTO.FiveQuestionsHintResponse.builder()
						.hints(hintDataList)
						.build()
					);
			});
	}

	public Mono<AIResponseDTO.FiveQuestionsCheckResponse> fiveQuestionsCheck(AIRequestDTO.FiveQuestionsCheck request) {

		Purpose purpose = Purpose.GAME;

		MultipartBodyBuilder builder = new MultipartBodyBuilder();

		try {
			byte[] voiceBytes = request.voice().getBytes();
			String originalFileName = request.voice().getOriginalFilename();

			if (originalFileName == null || originalFileName.isEmpty()) {
				originalFileName = "audio.mp4";
			}

			builder.part("voice", new ByteArrayResource(voiceBytes))
				.filename(originalFileName)
				.contentType(MediaType.parseMediaType("audio/mp4"));
		} catch (IOException e) {
			throw new GeneralException(CommonErrorStatus.VOICE_PARSING_ERROR);
		}
		builder.part("purpose", purpose.getName());
		if (request.sessionId() != null) {
			builder.part("session_id", request.sessionId());
		}

		return aiWebClient.post()
			.uri("/stt/transcribe")
			.body(BodyInserters.fromMultipartData(builder.build()))
			.retrieve()
			.bodyToMono(AIResponseDTO.STTResponse.class)
			.doOnError(WebClientResponseException.class, e -> {
				log.error("STT 에러: {}", e.getResponseBodyAsString());
			})
			.flatMap(sttRes -> {
				String recognizedText = sttRes.text();
				log.debug("아이가 한 말: {}", recognizedText);

				AIRequestDTO.FiveQuestionsCheckInner checkInnerReq = new AIRequestDTO.FiveQuestionsCheckInner(
					recognizedText,
					request.answer()
				);

				return aiWebClient.post()
					.uri("/game/fiveq/check")
					.bodyValue(checkInnerReq)
					.retrieve()
					.bodyToMono(AIResponseDTO.FiveQuestionsCheckAnswer.class)
					.doOnError(e -> log.error("FiveQ Check 에러: {}", e.getMessage()))
					.map(checkAns -> {
						Boolean correct = checkAns.correct();
						return AIResponseDTO.FiveQuestionsCheckResponse.of(
							correct, null
						);
					});
			});
	}

	public Mono<AIResponseDTO.RolePlayingResponse> rolePlaying(AIRequestDTO.RolePlaying request) {

		Purpose purpose = Purpose.ROLEPLAY;

		return aiWebClient.post()
			.uri("/chat/roleplay")
			.bodyValue(request)
			.retrieve()
			.bodyToMono(AIResponseDTO.RolePlayingResponse.class);
	}

	public Mono<AIResponseDTO.RolePlayingEndResponse> rolePlayingClose(AIRequestDTO.RolePlayingEnd request) {
		return aiWebClient.post()
			.uri("/chat/roleplay/close")
			.bodyValue(request)
			.retrieve()
			.bodyToMono(AIResponseDTO.RolePlayingEndResponse.class);
	}
}
