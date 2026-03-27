package com.greeni.api.ai.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greeni.api.ai.controller.docs.AIControllerDocs;
import com.greeni.api.ai.dto.AIRequestDTO;
import com.greeni.api.ai.dto.AIResponseDTO;
import com.greeni.api.ai.service.AIService;
import com.greeni.api.apiPayload.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AIController implements AIControllerDocs {

	private final AIService aiService;

	@Override
	@PostMapping("/five-questions/hint")
	public Mono<ResponseEntity<CommonResponse<AIResponseDTO.FiveQuestionsHintResponse>>> fiveQuestionsHint(
		AIRequestDTO.FiveQuestionsHint request) {
		return aiService.fiveQuestionsHint(request)
			.map(dto -> new ResponseEntity<>(CommonResponse.onSuccess(dto), HttpStatus.OK));
	}

	@Override
	@PostMapping(value = "/five-questions/check", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Mono<ResponseEntity<CommonResponse<AIResponseDTO.FiveQuestionsCheckResponse>>> fiveQuestionsCheck(
		AIRequestDTO.FiveQuestionsCheck request) {
		return aiService.fiveQuestionsCheck(request)
			.map(dto -> new ResponseEntity<>(CommonResponse.onSuccess(dto), HttpStatus.OK));
	}

	@Override
	@PostMapping(value = "/role-playing", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Mono<ResponseEntity<CommonResponse<AIResponseDTO.RolePlayingResponse>>> rolePlayingRequest(
		AIRequestDTO.RolePlaying request) {
		return aiService.rolePlaying(request)
			.map(dto -> new ResponseEntity<>(CommonResponse.onSuccess(dto), HttpStatus.OK));
	}

	@Override
	@PostMapping("/role-playing/close")
	public Mono<ResponseEntity<CommonResponse<AIResponseDTO.RolePlayingEndResponse>>> rolePlayingCloseRequest(
		AIRequestDTO.RolePlayingEnd request) {
		return aiService.rolePlayingClose(request)
			.map(dto -> new ResponseEntity<>(CommonResponse.onSuccess(dto), HttpStatus.OK));
	}
}
