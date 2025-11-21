package com.greeni.api.profiles.service;

import com.greeni.api.apiPayload.exception.GeneralException;
import com.greeni.api.apiPayload.status.MemberErrorStatus;
import com.greeni.api.apiPayload.status.ProfileErrorStatus;
import com.greeni.api.members.domain.Member;
import com.greeni.api.members.repository.MemberRepository;
import com.greeni.api.profiles.converter.ProfileConverter;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.dto.ProfileRequestDTO;
import com.greeni.api.profiles.dto.ProfileResponseDTO;
import com.greeni.api.profiles.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;

    // 프로필 생성
    @Override
    @Transactional
    public ProfileResponseDTO.CreateProfileResponse createProfile(Long memberId, ProfileRequestDTO.CreateProfileRequest dto) {
        // Member, Profile 엔티티 생성
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(MemberErrorStatus.NOT_EXIST_MEMBER));
        Profile profile = ProfileConverter.toProfile(member, dto);

        // 저장
        Profile saved = profileRepository.save(profile);

        // DTO 변환 후 반환
        return ProfileConverter.toCreateProfileResponseDTO(saved);
    }

    // 프로필 수정
    @Override
    @Transactional
    public ProfileResponseDTO.UpdateProfileResponse updateProfile(Long memberId, Long profileId, ProfileRequestDTO.UpdateProfileRequest dto) {
        // Profile 엔티티 조회
        Profile profile = profileRepository.findByIdAndMemberId(profileId, memberId)
                .orElseThrow(() -> new GeneralException(ProfileErrorStatus.PROFILE_NOT_FOUND));

        // 업데이트
        profile.update(dto.name(), dto.birth());

        // DTO 변환 후 반환
        return ProfileConverter.toUpdateProfileResponseDTO(profile);
    }
}
