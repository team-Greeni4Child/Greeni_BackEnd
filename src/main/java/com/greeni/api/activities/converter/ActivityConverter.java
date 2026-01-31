package com.greeni.api.activities.converter;

import com.greeni.api.activities.domain.Activity;
import com.greeni.api.activities.dto.ActivityResponseDTO;

import java.time.LocalDate;
import java.util.List;

public class ActivityConverter {

    public static ActivityResponseDTO.ActivityItem toActivityItemDTO(Activity activity) {
        return ActivityResponseDTO.ActivityItem.builder()
                .activityType(activity.getActivityType())
                .name(activity.getName())
                .description(activity.getDescription())
                .createdAt(activity.getCreatedAt())
                .build();
    }

    public static ActivityResponseDTO.DailyActivityGroup toDailyActivityGroupDTO(LocalDate date, List<Activity> activities) {
        List<ActivityResponseDTO.ActivityItem> items = activities.stream()
                .map(ActivityConverter::toActivityItemDTO)
                .toList();

        return ActivityResponseDTO.DailyActivityGroup.builder()
                .date(date)
                .activities(items)
                .build();
    }

    // entity list -> 목록 조회 응답 DTO
    public static ActivityResponseDTO.GetActivitySummaryListResponse toGetDailyActivityListResponseDTO(List<ActivityResponseDTO.DailyActivityGroup> days, int page, boolean hasNext) {
        return ActivityResponseDTO.GetActivitySummaryListResponse.builder()
                .days(days)
                .page(page)
                .hasNext(hasNext)
                .build();
    }
}
