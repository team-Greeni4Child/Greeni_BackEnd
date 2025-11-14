package com.greeni.api.members.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.members.dto.MemberRequestDTO;
import com.greeni.api.members.dto.MemberResponseDTO;
import com.greeni.api.security.auth.dto.AuthResponseDTO;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Member", description = "사용자 CRUD API")
public interface MemberControllerDocs {

	@Operation(summary = "회원가입 API")
	ResponseEntity<CommonResponse<MemberResponseDTO.toMemberResultDTO>> signUp(
		@RequestBody @Valid MemberRequestDTO.SignUpDTO request);

	@Operation(summary = "이메일 인증번호 전송 API")
	ResponseEntity<CommonResponse<?>> emailRequest(@RequestBody @Valid MemberRequestDTO.EmailDTO request);

	@Operation(summary = "비밀번호 찾기 API")
	ResponseEntity<CommonResponse<MemberResponseDTO.toMemberResultDTO>> findPassword(
		@RequestBody @Valid MemberRequestDTO.PasswdDTO request);

	@Operation(summary = "비밀번호 재설정 API")
	ResponseEntity<CommonResponse<MemberResponseDTO.toMemberResultDTO>> setPassword(
		@RequestBody @Valid MemberRequestDTO.ResetPwDTO request);

	@Operation(
		summary = "부모 페이지 비밀번호 확인 API",
		description = "입력한 비밀번호와 해당 사용자의 비밀번호가 일치하는지 확인하는 API",
		responses = {
			@ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다..",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.LoginResult.class))),
			@ApiResponse(responseCode = "MEMBER4004", description = "존재하지 않는 메일입니다"),
			@ApiResponse(responseCode = "MEMBER4007", description = "비밀번호가 일치하지 않습니다")
		}
	)
	ResponseEntity<CommonResponse<Object>> checkParentPassword(
		@RequestBody @Valid MemberRequestDTO.ParentPasswordDTO parentPasswordRequest,
		@AuthenticationPrincipal CustomUserDetails userDetails);
}
