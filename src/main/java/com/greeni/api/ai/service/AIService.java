package com.greeni.api.ai.service;

import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.greeni.api.ai.domain.Purpose;
import com.greeni.api.ai.dto.AIRequestDTO;
import com.greeni.api.ai.dto.AIResponseDTO;

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
								System.out.println("🚨 FastAPI가 거절한 이유: " + e.getResponseBodyAsString());
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

		return aiWebClient.post()
			.uri("/game/fiveq/check")
			.bodyValue(request)
			.retrieve()
			.bodyToMono(AIResponseDTO.FiveQuestionsCheckResponse.class);
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
