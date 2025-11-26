package com.greeni.api.badges.dto;

import lombok.Builder;

import java.util.List;

public class BadgeResponseDTO {

    @Builder
    public record GetBadgeListResponse(
            List<BadgeList> badgeLists,
            boolean hasNext  // Slice에서 제공
    ) {}

    @Builder
    public record BadgeList(
            Long badgeId,
            String name,
            String description
    ) {}
}
