package com.greeni.api.badges.converter;

import com.greeni.api.badges.dto.BadgeResponseDTO;
import com.greeni.api.profiles.domain.mapping.ProfileBadge;

import java.util.List;

public class BadgeConverter {

    // entity list -> 목록 조회 응답 DTO
    public static BadgeResponseDTO.GetBadgeListResponse toGetBadgeListResponseDTO(List<ProfileBadge> badges) {
        return BadgeResponseDTO.GetBadgeListResponse.builder()
                .badgeLists(badges.stream()
                        .map(badge -> BadgeResponseDTO.BadgeList.builder()
                                .badgeId(badge.getBadge().getId())
                                .name(badge.getBadge().getName())
                                .description(badge.getBadge().getDescription())
                                .build())
                        .toList())
                .build();
    }
}
