package com.greeni.api.activities.dto;

import java.util.List;

import lombok.Builder;

public class ActivityResponseDTO {

	@Builder
	public record DailyList(
		List<String> ActivityList
	) {
	}
}
