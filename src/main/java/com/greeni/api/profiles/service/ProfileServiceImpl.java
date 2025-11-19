package com.greeni.api.profiles.service;

import com.greeni.api.profiles.converter.ProfileConverter;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.dto.ProfileRequestDTO;
import com.greeni.api.profiles.dto.ProfileResponseDTO;
import com.greeni.api.profiles.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    // 프로필 생성
    @Override
    @Transactional
    public ProfileResponseDTO.CreateProfileResponse createProfile(ProfileRequestDTO.CreateProfileRequest dto) {
        // Profile 엔티티 생성
        Profile profile = ProfileConverter.toProfile(dto);

        // 저장
        Profile saved = profileRepository.save(profile);

        // DTO 변환 후 반환
        return ProfileConverter.toCreateProfileResponseDTO(saved);
    }
}
