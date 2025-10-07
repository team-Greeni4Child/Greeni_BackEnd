package com.greeni.api.members.converter;

import com.greeni.api.members.domain.Member;
import com.greeni.api.members.dto.MemberRequestDTO;
import com.greeni.api.members.dto.MemberResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

public class MemberConverter {

    public static Member toMember(MemberRequestDTO.SignUpDTO request){
        return Member.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    public static MemberResponseDTO.toMemberResultDTO toMemberResultDTO(Member member){
        return MemberResponseDTO.toMemberResultDTO.builder()
                .memberId(member.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
