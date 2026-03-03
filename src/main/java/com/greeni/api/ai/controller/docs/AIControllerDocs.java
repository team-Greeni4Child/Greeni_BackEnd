package com.greeni.api.ai.controller.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.greeni.api.ai.dto.AIRequestDTO;
import com.greeni.api.ai.dto.AIResponseDTO;
import com.greeni.api.apiPayload.CommonResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@Tag(name = "AI", description = "Backend-AI 연동 API")
public interface AIControllerDocs {

	@Operation(
		summary = "AI: 다섯고개 힌트 생성",
		description = "다섯고개: AI의 힌트를 받아오는 API"
	)
	@ApiResponses({
		@ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다..",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = AIResponseDTO.class))),
	})
	Mono<ResponseEntity<CommonResponse<AIResponseDTO.FiveQuestionsHintResponse>>> fiveQuestionsHint(
		@Valid @RequestBody AIRequestDTO.FiveQuestionsHint request);

	@Operation(
		summary = "AI: 다섯고개 정답 체크",
		description = "다섯고개: AI에게 정답을 확인하는 API"
	)
	@ApiResponses({
		@ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다..",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = AIResponseDTO.class))),
	})
	Mono<ResponseEntity<CommonResponse<AIResponseDTO.FiveQuestionsCheckResponse>>> fiveQuestionsCheck(
		@Valid @RequestBody AIRequestDTO.FiveQuestionsCheck request);

	@Operation(
		summary = "AI: 역할놀이 대화 요청",
		description = "역할놀이: AI에게 대화를 요청하는 API"
	)
	@ApiResponses({
		@ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다..",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = AIResponseDTO.class))),
	})
	Mono<ResponseEntity<CommonResponse<AIResponseDTO.RolePlayingResponse>>> rolePlayingRequest(
		@Valid @RequestBody AIRequestDTO.RolePlaying request);

	@Operation(
		summary = "AI: 역할놀이 대화 종료",
		description = "역할놀이: AI에게 대화 종료를 요청하는 API"
	)
	@ApiResponses({
		@ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다..",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = AIResponseDTO.class))),
	})
	Mono<ResponseEntity<CommonResponse<AIResponseDTO.RolePlayingEndResponse>>> rolePlayingCloseRequest(
		@Valid @RequestBody AIRequestDTO.RolePlayingEnd request);
}
