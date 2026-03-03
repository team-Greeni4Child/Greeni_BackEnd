package com.greeni.api.ai.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.greeni.api.ai.dto.AIRequestDTO;
import com.greeni.api.ai.dto.AIResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AIService {

	private final WebClient aiWebClient;

	public Mono<AIResponseDTO.FiveQuestionsHintResponse> fiveQuestionsHint(AIRequestDTO.FiveQuestionsHint request) {
		return aiWebClient.post()
			.uri("/game/fiveq/hint")
			.bodyValue(request)
			.retrieve()
			.bodyToMono(AIResponseDTO.FiveQuestionsHintResponse.class);
	}

	public Mono<AIResponseDTO.FiveQuestionsCheckResponse> fiveQuestionsCheck(AIRequestDTO.FiveQuestionsCheck request) {
		return aiWebClient.post()
			.uri("/game/fiveq/check")
			.bodyValue(request)
			.retrieve()
			.bodyToMono(AIResponseDTO.FiveQuestionsCheckResponse.class);
	}

	public Mono<AIResponseDTO.RolePlayingResponse> rolePlaying(AIRequestDTO.RolePlaying request) {
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
