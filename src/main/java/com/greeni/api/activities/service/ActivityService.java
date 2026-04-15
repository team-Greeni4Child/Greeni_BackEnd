package com.greeni.api.activities.service;

import java.time.LocalDateTime;

import com.greeni.api.activities.dto.ActivityRequestDTO;
import com.greeni.api.activities.dto.ActivityResponseDTO;
import com.greeni.api.profiles.domain.Profile;

public interface ActivityService {

	// 활동 요약 목록 조회
	ActivityResponseDTO.GetActivitySummaryListResponse getActivitySummaryList(Long memberId, Long profileId,
		LocalDateTime cursorCreatedAt, Long cursorId, int size);

	ActivityResponseDTO.DailyList getDailyActivityList(Long memberId, Long profileId);

	ActivityResponseDTO.ActivityCreateResponse createFiveQuestionsActivity(Long memberId,
		ActivityRequestDTO.FiveQuestionCreateRequest request);

	ActivityResponseDTO.ActivityCreateResponse createRolePlayingActivity(Long memberId,
		ActivityRequestDTO.RolePlayingCreateRequest request);

	//void checkTodayActivity(Profile profile);

	void checkTodayActivity(Long profileId);

}
