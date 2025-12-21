package com.greeni.api.badges.controller;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.badges.dto.BadgeResponseDTO;
import com.greeni.api.badges.service.BadgeService;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greeni.api.badges.docs.BadgeControllerDocs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
public class BadgeController implements BadgeControllerDocs {

    private final BadgeService badgeService;

    @GetMapping("/list")
    public ResponseEntity<CommonResponse<BadgeResponseDTO.GetBadgeListResponse>> findBadgeList(
            @RequestParam Long profileId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        BadgeResponseDTO.GetBadgeListResponse result = badgeService.getBadgeList(customUserDetails.getId(), profileId);
        return new ResponseEntity<>(CommonResponse.onSuccess(result), HttpStatus.OK);
    }
}
