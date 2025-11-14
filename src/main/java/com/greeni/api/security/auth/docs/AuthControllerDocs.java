package com.greeni.api.security.auth.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.security.auth.dto.AuthRequestDTO;
import com.greeni.api.security.auth.dto.AuthResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Tag(name = "Auth", description = "인증/인가 API")
public interface AuthControllerDocs {

	@Operation(
		summary = "로그인 API",
		description = "사용자의 이메일과 비밀번호로 로그인을 진행하는 API",
		responses = {
			@ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다..",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.LoginResult.class))),
			@ApiResponse(responseCode = "MEMBER4004", description = "존재하지 않는 메일입니다"),
			@ApiResponse(responseCode = "MEMBER4007", description = "비밀번호가 일치하지 않습니다")
		}
	)
	ResponseEntity<CommonResponse<AuthResponseDTO.LoginResult>> login(
		@RequestBody @Valid AuthRequestDTO.Login loginRequest, HttpServletResponse response);

	@Operation(
		summary = "JWT 재발급 API",
		description = "Refresh Token을 입력받아, Access Token과 Refresh Token을 재발급 하는 API",
		responses = {
			@ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다..",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.LoginResult.class))),
			@ApiResponse(responseCode = "MEMBER4004", description = "존재하지 않는 메일입니다")
		}
	)
	ResponseEntity<CommonResponse<AuthResponseDTO.LoginResult>> reissue(
		@RequestHeader(name = "Refresh-Token") String refreshToken, HttpServletResponse response);

	@Operation(
		summary = "로그아웃 API",
		description = "사용자의 액세스 토큰과 리프레시 토큰을 블랙리스트화하는 API",
		responses = {
			@ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다.."),
			@ApiResponse(responseCode = "MEMBER4001", description = "존재하지 않는 메일입니다")
		}
	)
	ResponseEntity<CommonResponse<Object>> logout(HttpServletRequest request);

	@Operation(
		summary = "회원탈퇴 API",
		description = "사용자의 액세스 토큰과 리프레시 토큰을 블랙리스트화하고, 사용자를 삭제하는 API",
		responses = {
			@ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다.."),
			@ApiResponse(responseCode = "MEMBER4001", description = "존재하지 않는 메일입니다")
		}
	)
	ResponseEntity<CommonResponse<Object>> deleteMember(HttpServletRequest request);
}
