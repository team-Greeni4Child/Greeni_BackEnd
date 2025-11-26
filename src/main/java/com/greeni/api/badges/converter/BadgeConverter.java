package com.greeni.api.badges.converter;

import com.greeni.api.badges.domain.Badge;
import com.greeni.api.badges.dto.BadgeResponseDTO;
import com.greeni.api.profiles.domain.mapping.ProfileBadge;
import org.springframework.data.domain.Slice;

import java.util.List;

public class BadgeConverter {

    // Slice<ProfileBadge> -> 목록 조회 응답 DTO
    public static BadgeResponseDTO.GetBadgeListResponse toGetBadgeListResponseDTO(Slice<ProfileBadge> badges) {
        List<BadgeResponseDTO.BadgeList> badgeLists = badges.stream()
                .map(profileBadge -> {
                    Badge badge = profileBadge.getBadge();
                    return BadgeResponseDTO.BadgeList.builder()
                            .badgeId(badge.getId())
                            .name(badge.getName())
                            .description(badge.getDescription())
                            .build();
                })
                .toList();

        return BadgeResponseDTO.GetBadgeListResponse.builder()
                .badgeLists(badgeLists)
                .hasNext(badges.hasNext())
                .build();
    }
}
