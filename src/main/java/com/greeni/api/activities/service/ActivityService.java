package com.greeni.api.activities.service;

import com.greeni.api.activities.dto.ActivityResponseDTO;

import java.time.LocalDateTime;

public interface ActivityService {

    // 활동 요약 목록 조회
    ActivityResponseDTO.GetActivitySummaryListResponse getActivitySummaryList(Long memberId, Long profileId, LocalDateTime cursorCreatedAt, Long cursorId, int size);

	ActivityResponseDTO.DailyList getDailyActivityList(Long memberId, Long profileId);
}
