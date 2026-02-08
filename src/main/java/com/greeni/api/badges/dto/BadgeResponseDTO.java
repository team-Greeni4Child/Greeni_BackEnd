package com.greeni.api.badges.dto;

import java.util.List;

import lombok.Builder;

public class BadgeResponseDTO {

	@Builder
	public record GetBadgeListResponse(
		List<BadgeList> badgeLists
	) {
	}

	@Builder
	public record BadgeList(
		Long badgeId,
		String name,
		String imageUrl
	) {
	}
}
