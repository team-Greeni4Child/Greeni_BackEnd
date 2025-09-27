package com.greeni.api.members.controller;

import com.greeni.api.apiPayload.ApiResponse;
import com.greeni.api.apiPayload.status.SuccessStatus;
import com.greeni.api.members.dto.MemberRequestDTO;
import com.greeni.api.members.dto.MemberResponseDTO;
import com.greeni.api.members.service.MemberService;
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
    public ResponseEntity<ApiResponse<MemberResponseDTO.toJoinResultDTO>> signUp(@RequestBody @Valid MemberRequestDTO.SignUpDTO request){
        MemberResponseDTO.toJoinResultDTO result = memberService.signUpMember(request);
        return new ResponseEntity<>(ApiResponse.created(result),HttpStatus.CREATED);
    }

    @PostMapping("/email")
    public ResponseEntity<ApiResponse<?>> emailRequest(@RequestBody @Valid MemberRequestDTO.EmailDTO request){
        memberService.sendEmail(request);
        return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
    }

}
