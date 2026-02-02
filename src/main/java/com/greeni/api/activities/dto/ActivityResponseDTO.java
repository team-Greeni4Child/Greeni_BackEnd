package com.greeni.api.activities.dto;

import com.greeni.api.activities.domain.enums.ActivityType;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ActivityResponseDTO {

    @Builder
    public record GetActivitySummaryListResponse(
            List<DailyActivityGroup> days,
            LocalDateTime nextCursorCreatedAt,
            Long nextCursorId,
            boolean hasNext
    ) {}

    @Builder
    public record DailyActivityGroup(
            LocalDate date,
            List<ActivityItem> activities
    ) {}

    @Builder
    public record ActivityItem(
            ActivityType activityType,
            String name,
            String description,
            LocalDateTime createdAt
    ) {}

	@Builder
	public record DailyList(
		List<String> ActivityList
	) {}
}
