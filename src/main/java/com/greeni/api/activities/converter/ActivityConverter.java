package com.greeni.api.activities.converter;

import java.util.Collections;
import java.util.List;

import com.greeni.api.activities.domain.Activity;
import com.greeni.api.activities.dto.ActivityResponseDTO;

public class ActivityConverter {

	public static ActivityResponseDTO.DailyList toDailyActivityListResponseDTO(List<Activity> activityList) {

		if (activityList.isEmpty()) {
			return new ActivityResponseDTO.DailyList(Collections.emptyList());
		}

		return new ActivityResponseDTO.DailyList(
			activityList.stream().map(Activity::getDescription).toList()
		);
	}
}
