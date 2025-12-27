package com.greeni.api.members.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.members.controller.docs.MemberControllerDocs;
import com.greeni.api.members.dto.MemberRequestDTO;
import com.greeni.api.members.dto.MemberResponseDTO;
import com.greeni.api.members.service.MemberService;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController implements MemberControllerDocs {

	private final MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<CommonResponse<MemberResponseDTO.toMemberResultDTO>> signUp(
		@RequestBody @Valid MemberRequestDTO.SignUpDTO request) {
		MemberResponseDTO.toMemberResultDTO result = memberService.signUpMember(request);
		return new ResponseEntity<>(CommonResponse.created(result), HttpStatus.CREATED);
	}

	@PostMapping("/email")
	public ResponseEntity<CommonResponse<?>> emailRequest(@RequestBody @Valid MemberRequestDTO.EmailDTO request) {
		memberService.sendEmail(request);
		return ResponseEntity.ok().body(CommonResponse.onSuccess(null));
	}

	@PostMapping("/password")
	public ResponseEntity<CommonResponse<MemberResponseDTO.toMemberResultDTO>> findPassword(
		@RequestBody @Valid MemberRequestDTO.PasswdDTO request) {
		return ResponseEntity.ok().body(CommonResponse.onSuccess(memberService.findPw(request)));
	}

	@PostMapping("/password/reset")
	public ResponseEntity<CommonResponse<MemberResponseDTO.toMemberResultDTO>> setPassword(
		@RequestBody @Valid MemberRequestDTO.ResetPwDTO request) {
		return ResponseEntity.ok().body(CommonResponse.onSuccess(memberService.resetPw(request)));
	}

	@PostMapping("/parent-password")
	public ResponseEntity<CommonResponse<Object>> checkParentPassword(
		@RequestBody @Valid MemberRequestDTO.ParentPasswordDTO parentPasswordRequest,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		memberService.checkParentPassword(parentPasswordRequest, userDetails);
		return ResponseEntity.ok().body(CommonResponse.onSuccess(null));
	}
}
