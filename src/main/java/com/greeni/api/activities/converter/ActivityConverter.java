package com.greeni.api.activities.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import com.greeni.api.activities.domain.Activity;
import com.greeni.api.activities.domain.enums.ActivityType;
import com.greeni.api.activities.dto.ActivityResponseDTO;
import com.greeni.api.profiles.domain.Profile;

public class ActivityConverter {

	public static ActivityResponseDTO.ActivityItem toActivityItemDTO(Activity activity) {
		return ActivityResponseDTO.ActivityItem.builder()
			.activityType(activity.getActivityType())
			.name(activity.getName())
			.description(activity.getDescription())
			.createdAt(activity.getCreatedAt())
			.build();
	}

	public static ActivityResponseDTO.DailyActivityGroup toDailyActivityGroupDTO(LocalDate date,
		List<Activity> activities) {
		List<ActivityResponseDTO.ActivityItem> items = activities.stream()
			.map(ActivityConverter::toActivityItemDTO)
			.toList();

		return ActivityResponseDTO.DailyActivityGroup.builder()
			.date(date)
			.activities(items)
			.build();
	}

	// entity list -> 목록 조회 응답 DTO
	public static ActivityResponseDTO.GetActivitySummaryListResponse toGetDailyActivityListResponseDTO(
		List<ActivityResponseDTO.DailyActivityGroup> days, LocalDateTime nextCursorCreatedAt, Long nextCursorId,
		boolean hasNext) {
		return ActivityResponseDTO.GetActivitySummaryListResponse.builder()
			.days(days)
			.nextCursorCreatedAt(nextCursorCreatedAt)
			.nextCursorId(nextCursorId)
			.hasNext(hasNext)
			.build();
	}

	public static ActivityResponseDTO.DailyList toDailyActivityListResponseDTO(List<Activity> activityList) {

		if (activityList.isEmpty()) {
			return new ActivityResponseDTO.DailyList(Collections.emptyList());
		}

		return new ActivityResponseDTO.DailyList(
			activityList.stream().map(Activity::getDescription).toList()
		);
	}

	public static Activity toActivity(ActivityType activityType, String description, Profile profile,
		String badgeName) {
		return Activity.builder()
			.activityType(activityType)
			.name(activityType == ActivityType.BADGE ? badgeName : activityType.getName())
			.description(description)
			.profile(profile)
			.build();
	}

	public static ActivityResponseDTO.ActivityCreateResponse toActivityCreateResponseDTO(Activity activity) {
		return ActivityResponseDTO.ActivityCreateResponse.builder()
			.activityId(activity.getId())
			.createdAt(LocalDateTime.now())
			.build();
	}
}
