package com.greeni.api.badges.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greeni.api.activities.converter.ActivityConverter;
import com.greeni.api.activities.domain.enums.ActivityType;
import com.greeni.api.activities.repository.ActivityRepository;
import com.greeni.api.apiPayload.handler.GeneralException;
import com.greeni.api.apiPayload.status.BadgeErrorStatus;
import com.greeni.api.apiPayload.status.ProfileErrorStatus;
import com.greeni.api.badges.converter.BadgeConverter;
import com.greeni.api.badges.domain.Badge;
import com.greeni.api.badges.dto.BadgeResponseDTO;
import com.greeni.api.badges.repository.BadgeRepository;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.domain.mapping.ProfileBadge;
import com.greeni.api.profiles.repository.ProfileBadgeRepository;
import com.greeni.api.profiles.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BadgeServiceImpl implements BadgeService {

	private static final List<Integer> BADGE_THRESHOLDS = List.of(5, 10, 30, 50, 100);
	private final ProfileRepository profileRepository;
	private final ProfileBadgeRepository profileBadgeRepository;
	private final ActivityRepository activityRepository;
	private final BadgeRepository badgeRepository;

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

	@Override
	@Transactional
	public void checkAndAwardBadge(Profile profile, ActivityType activityType) {

		long currentCount = getCountByActivityType(profile, activityType);

		if (BADGE_THRESHOLDS.contains((int)currentCount)) {
			awardBadge(profile, activityType, (int)currentCount);
		}
	}

	private long getCountByActivityType(Profile profile, ActivityType activityType) {
		return switch (activityType) {
			case BADGE -> profileBadgeRepository
				.countByProfile(profile);
			case DIARY, ROLE_PLAYING, FIVE_QUESTIONS -> activityRepository
				.countByProfileAndActivityType(profile, activityType);
			case ATTENDANCE -> profile.getAttendance();
			default -> 0L;
		};
	}

	private void awardBadge(Profile profile, ActivityType activityType, int count) {

		String badgeName = generateBadgeName(activityType, count);

		Badge badge = badgeRepository.findByName(badgeName)
			.orElseThrow(() -> new GeneralException(BadgeErrorStatus.BADGE_NOT_FOUND));

		boolean alreadyHas = profileBadgeRepository.existsByProfileAndBadge(profile, badge);
		if (!alreadyHas) {
			String description = badgeName + "배지를 획득했어요.";
			activityRepository.save(ActivityConverter.toActivity(ActivityType.BADGE, description, profile, badgeName));

			ProfileBadge profileBadge = ProfileBadge.builder()
				.profile(profile)
				.badge(badge)
				.build();
			profileBadgeRepository.save(profileBadge);
		}
	}

	private String generateBadgeName(ActivityType activityType, int count) {
		return switch (activityType) {
			case ATTENDANCE -> count + "일 출석";
			case BADGE -> "배지 " + count + "회 획득";
			case DIARY -> "일기 " + count + "회 작성";
			case FIVE_QUESTIONS -> "다섯고개 " + count + "회 수행";
			case ROLE_PLAYING -> "역할놀이 " + count + "회 수행";
			default -> "";
		};
	}
}
