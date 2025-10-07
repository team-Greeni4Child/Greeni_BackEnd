package com.greeni.api.members.controller;

import com.greeni.api.apiPayload.ApiResponse;
import com.greeni.api.apiPayload.status.SuccessStatus;
import com.greeni.api.members.dto.MemberRequestDTO;
import com.greeni.api.members.dto.MemberResponseDTO;
import com.greeni.api.members.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입 API")
    public ResponseEntity<ApiResponse<MemberResponseDTO.toMemberResultDTO>> signUp(@RequestBody @Valid MemberRequestDTO.SignUpDTO request){
        MemberResponseDTO.toMemberResultDTO result = memberService.signUpMember(request);
        return new ResponseEntity<>(ApiResponse.created(result),HttpStatus.CREATED);
    }

    @PostMapping("/email")
    @Operation(summary = "이메일 인증번호 전송 API")
    public ResponseEntity<ApiResponse<?>> emailRequest(@RequestBody @Valid MemberRequestDTO.EmailDTO request){
        memberService.sendEmail(request);
        return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
    }

    @PostMapping("/password")
    @Operation(summary = "비밀번호 찾기 API")
    public ResponseEntity<ApiResponse<MemberResponseDTO.toMemberResultDTO>> findPassword(@RequestBody @Valid MemberRequestDTO.PasswdDTO request){
        return ResponseEntity.ok().body(ApiResponse.onSuccess(memberService.findPw(request)));
    }

    @PostMapping("/password/reset")
    @Operation(summary = "비밀번호 재설정 API")
    public ResponseEntity<ApiResponse<MemberResponseDTO.toMemberResultDTO>> setPassword(@RequestBody @Valid MemberRequestDTO.ResetPwDTO request){
        return ResponseEntity.ok().body(ApiResponse.onSuccess(memberService.resetPw(request)));
    }

}
