package com.greeni.api.badges.service;

import com.greeni.api.apiPayload.exception.GeneralException;
import com.greeni.api.apiPayload.status.ProfileErrorStatus;
import com.greeni.api.badges.converter.BadgeConverter;
import com.greeni.api.badges.dto.BadgeResponseDTO;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.domain.mapping.ProfileBadge;
import com.greeni.api.profiles.repository.ProfileBadgeRepository;
import com.greeni.api.profiles.repository.ProfileRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BadgeServiceImpl implements BadgeService {

    private final ProfileRepository profileRepository;
    private final ProfileBadgeRepository profileBadgeRepository;

    @Override
    public BadgeResponseDTO.GetBadgeListResponse getBadgeList(Long memberId, Long profileId, Pageable pageable) {
        // Badge 리스트 조회
        Slice<ProfileBadge> badges = profileBadgeRepository.findByProfileIdOrderByIdDesc(profileId, pageable);

        // DTO 변환 후 반환
        return BadgeConverter.toGetBadgeListResponseDTO(badges);
    }
}
