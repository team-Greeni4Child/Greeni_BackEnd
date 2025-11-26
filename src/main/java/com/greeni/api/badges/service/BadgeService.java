package com.greeni.api.badges.service;

import org.springframework.data.domain.Pageable;
import com.greeni.api.badges.dto.BadgeResponseDTO;

public interface BadgeService {

    // 배지 목록 조회
    BadgeResponseDTO.GetBadgeListResponse getBadgeList(Long memberId, Long profileId, Pageable pageable);
}
