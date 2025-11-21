package com.greeni.api.profiles.service;

import com.greeni.api.profiles.dto.ProfileRequestDTO;
import com.greeni.api.profiles.dto.ProfileResponseDTO;

import java.time.LocalDate;

public interface ProfileService {

    // 프로필 생성
    ProfileResponseDTO.CreateProfileResponse createProfile(Long memberId, ProfileRequestDTO.CreateProfileRequest dto);

    // 프로필 수정
    public ProfileResponseDTO.UpdateProfileResponse updateProfile(Long memberId, Long profileId, ProfileRequestDTO.UpdateProfileRequest dto);
}
