package com.greeni.api.security.auth.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.security.auth.dto.AuthRequestDTO;
import com.greeni.api.security.auth.dto.AuthResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Tag(name = "Auth", description = "인증/인가 API")
public interface AuthControllerDocs {

	@Operation(
		summary = "로그인 API",
		description = "사용자의 이메일과 비밀번호로 로그인을 진행하는 API",
		responses = {
			@ApiResponse(responseCode = "COMMON200", description = "성공입니다.",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.LoginResult.class))),
			@ApiResponse(responseCode = "MEMBER4004", description = "존재하지 않는 메일입니다"),
			@ApiResponse(responseCode = "MEMBER4007", description = "비밀번호가 일치하지 않습니다")
		}
	)
	ResponseEntity<CommonResponse<AuthResponseDTO.LoginResult>> login(
		@RequestBody @Valid AuthRequestDTO.Login loginRequest, HttpServletResponse response);
}
