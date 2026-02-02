package com.greeni.api.activities.controller;

import com.greeni.api.activities.dto.ActivityResponseDTO;
import com.greeni.api.activities.service.ActivityService;
import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greeni.api.activities.controller.docs.ActivityControllerDocs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/activites")
@RequiredArgsConstructor
public class ActivityController implements ActivityControllerDocs {

    private final ActivityService activityService;

    // 활동 요약 목록 조회
    @GetMapping("/day/list")
    public ResponseEntity<CommonResponse<ActivityResponseDTO.GetActivitySummaryListResponse>> findActivitySummaryList(
            @RequestParam Long profileId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        ActivityResponseDTO.GetActivitySummaryListResponse result = activityService.getActivitySummaryList(customUserDetails.getId(), profileId, page, size);
        return new ResponseEntity<>(CommonResponse.onSuccess(result), HttpStatus.OK);
    }

	@Override
	@GetMapping("/day")
	public ResponseEntity<CommonResponse<ActivityResponseDTO.DailyList>> getDailyActivityList(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestParam Long profileId) {
		ActivityResponseDTO.DailyList result = activityService
			.getDailyActivityList(customUserDetails.getId(), profileId);
		return new ResponseEntity<>(CommonResponse.onSuccess(result), HttpStatus.OK);
	}
}
