package com.greeni.api.badges.service;

import com.greeni.api.apiPayload.handler.GeneralException;
import com.greeni.api.apiPayload.status.ProfileErrorStatus;
import com.greeni.api.badges.converter.BadgeConverter;
import com.greeni.api.badges.dto.BadgeResponseDTO;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.domain.mapping.ProfileBadge;
import com.greeni.api.profiles.repository.ProfileBadgeRepository;
import com.greeni.api.profiles.repository.ProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BadgeServiceImpl implements BadgeService {

	private final ProfileRepository profileRepository;
	private final ProfileBadgeRepository profileBadgeRepository;

	@Override
	public BadgeResponseDTO.GetBadgeListResponse getBadgeList(Long memberId, Long profileId) {
		// Profile 엔티티 조회
		Profile profile = profileRepository.findById(profileId)
			.orElseThrow(() -> new GeneralException(ProfileErrorStatus.PROFILE_NOT_FOUND));

		// 이 Profile이 현재 로그인한 회원의 것인지 검증
		if (!profile.getMember().getId().equals(memberId)) {
			throw new GeneralException(ProfileErrorStatus.UNAUTHORIZED_PROFILE_ACCESS);
		}

		// Badge 리스트 조회
		List<ProfileBadge> badges = profileBadgeRepository.findByProfileIdOrderByCreatedAtDesc(profileId);

		// DTO 변환 후 반환
		return BadgeConverter.toGetBadgeListResponseDTO(badges);
	}
}
