package com.greeni.api.ai.service;

import com.greeni.api.ai.domain.Purpose;
import com.greeni.api.ai.domain.RolePlayingType;
import com.greeni.api.ai.dto.AIRequestDTO;
import com.greeni.api.ai.dto.AIResponseDTO;
import com.greeni.api.apiPayload.handler.GeneralException;
import com.greeni.api.apiPayload.status.CommonErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional()
public class AIService {

    private final WebClient aiWebClient;

    public Mono<AIResponseDTO.FiveQuestionsHintResponse> fiveQuestionsHint(AIRequestDTO.FiveQuestionsHint request) {

        Purpose purpose = Purpose.FIVEQ;

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
                                        .bodyToMono(String.class)
                                        .doOnError(WebClientResponseException.class, e -> {
                                            log.error("TTS 에러: {}", e.getResponseBodyAsString());
                                        })
                                        .map(base64Audio -> new AIResponseDTO.TextVoiceData(hintText, base64Audio));
                            })
                            .collectList()
                            .map(hintDataList -> AIResponseDTO.FiveQuestionsHintResponse.builder()
                                    .hints(hintDataList)
                                    .build()
                            );
                });
    }

    public Mono<AIResponseDTO.FiveQuestionsCheckResponse> fiveQuestionsCheck(AIRequestDTO.FiveQuestionsCheck request) {

        Purpose purpose = Purpose.FIVEQ;

        MultipartBodyBuilder builder = buildVoiceMultipart(request.voice(), purpose, null);

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
        RolePlayingType type = RolePlayingType.valueOf(request.role().toUpperCase());

        MultipartBodyBuilder builder = buildVoiceMultipart(request.voice(), purpose, null);

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

                    AIRequestDTO.RolePlayingInner rolePlayingReq = new AIRequestDTO.RolePlayingInner(
                            request.sessionId(), type, recognizedText, null, null, null
                    );

                    return aiWebClient.post()
                            .uri("/chat/roleplay")
                            .bodyValue(rolePlayingReq)
                            .retrieve()
                            .bodyToMono(AIResponseDTO.RolePlayingInnerResponse.class)
                            .doOnError(e -> log.error("RolePlaying 에러: {}", e.getMessage()))
                            .flatMap(roleRes -> {
                                AIRequestDTO.TTSRequest ttsRequest = new AIRequestDTO.TTSRequest(
                                        purpose, roleRes.reply(), null, null, null
                                );
                                return aiWebClient.post()
                                        .uri("/tts/speak")
                                        .bodyValue(ttsRequest)
                                        .retrieve()
                                        .bodyToMono(String.class)
                                        .doOnError(WebClientResponseException.class, e -> {
                                            log.error("TTS 에러: {}", e.getResponseBodyAsString());
                                        })
                                        .map(base64Audio -> AIResponseDTO.RolePlayingResponse.of(
                                                request.sessionId(), base64Audio, roleRes.reply(), roleRes.turn()
                                        ));
                            });
                });
    }

    public Mono<AIResponseDTO.RolePlayingEndResponse> rolePlayingClose(AIRequestDTO.RolePlayingEnd request) {
        return aiWebClient.post()
                .uri("/chat/roleplay/close")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AIResponseDTO.RolePlayingEndResponse.class)
                .map(result -> AIResponseDTO.RolePlayingEndResponse.of(request.sessionId()));
    }

    private MultipartBodyBuilder buildVoiceMultipart(MultipartFile voice, Purpose purpose, String sessionId) {

        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        try {
            byte[] voiceBytes = voice.getBytes();
            String originalFileName = voice.getOriginalFilename();

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
        if (sessionId != null) {
            builder.part("session_id", sessionId);
        }

		return builder;
    }
}
