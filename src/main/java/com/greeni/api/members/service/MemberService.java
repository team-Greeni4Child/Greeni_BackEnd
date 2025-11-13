package com.greeni.api.members.service;

import com.greeni.api.members.domain.Member;
import com.greeni.api.members.dto.MemberRequestDTO;
import com.greeni.api.members.dto.MemberResponseDTO;

public interface MemberService {

	MemberResponseDTO.toMemberResultDTO signUpMember(MemberRequestDTO.SignUpDTO request);

	void checkEmailCode(String email, String code);

	void sendEmail(MemberRequestDTO.EmailDTO request);

	MemberResponseDTO.toMemberResultDTO findPw(MemberRequestDTO.PasswdDTO request);

	MemberResponseDTO.toMemberResultDTO resetPw(MemberRequestDTO.ResetPwDTO request);

	Member findMemberByEmail(String email);
}
