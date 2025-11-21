package com.greeni.api.profiles.service;

import com.greeni.api.members.domain.Member;
import com.greeni.api.profiles.dto.ProfileRequestDTO;
import com.greeni.api.profiles.dto.ProfileResponseDTO;

public interface ProfileService {

    // 프로필 생성
    ProfileResponseDTO.CreateProfileResponse createProfile(Long memberId, ProfileRequestDTO.CreateProfileRequest dto);
}
