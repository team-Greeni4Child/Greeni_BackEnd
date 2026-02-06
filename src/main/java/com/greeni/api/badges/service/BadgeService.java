package com.greeni.api.badges.service;

import com.greeni.api.activities.domain.enums.ActivityType;
import com.greeni.api.badges.dto.BadgeResponseDTO;
import com.greeni.api.profiles.domain.Profile;

public interface BadgeService {

	// 배지 목록 조회
	BadgeResponseDTO.GetBadgeListResponse getBadgeList(Long memberId, Long profileId);

	void checkAndAwardBadge(Profile profile, ActivityType activityType);
}
