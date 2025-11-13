package com.greeni.api.members.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.members.dto.MemberRequestDTO;
import com.greeni.api.members.dto.MemberResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
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
}
