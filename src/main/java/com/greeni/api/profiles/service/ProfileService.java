package com.greeni.api.profiles.service;

import com.greeni.api.profiles.dto.ProfileRequestDTO;
import com.greeni.api.profiles.dto.ProfileResponseDTO;

public interface ProfileService {

    // 프로필 생성
    ProfileResponseDTO.CreateProfileResponse createProfile(Long memberId, ProfileRequestDTO.CreateProfileRequest dto);

    // 프로필 수정
    ProfileResponseDTO.UpdateProfileResponse updateProfile(Long memberId, Long profileId, ProfileRequestDTO.UpdateProfileRequest dto);

    // 프로필 삭제
    void deleteProfile(Long memberId, Long profileId);

    // 프로필 목록 조회
    ProfileResponseDTO.GetProfileListResponse getProfileList(Long memberId);

    // 프로필 단일 조회
    ProfileResponseDTO.GetProfileResponse getProfile(Long memberId, Long profileId);
}
