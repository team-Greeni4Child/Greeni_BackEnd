package com.greeni.api.activities.service;

import com.greeni.api.activities.dto.ActivityResponseDTO;

public interface ActivityService {
	ActivityResponseDTO.DailyList getDailyActivityList(Long memberId, Long profileId);
}
