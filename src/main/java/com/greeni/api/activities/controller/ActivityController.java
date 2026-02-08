package com.greeni.api.activities.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greeni.api.activities.controller.docs.ActivityControllerDocs;
import com.greeni.api.activities.dto.ActivityRequestDTO;
import com.greeni.api.activities.dto.ActivityResponseDTO;
import com.greeni.api.activities.service.ActivityService;
import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;

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
		@RequestParam(required = false) LocalDateTime cursorCreatedAt,
		@RequestParam(required = false) Long cursorId,
		@RequestParam(defaultValue = "8") int size,
		@AuthenticationPrincipal CustomUserDetails customUserDetails
	) {
		ActivityResponseDTO.GetActivitySummaryListResponse result = activityService.getActivitySummaryList(
			customUserDetails.getId(), profileId, cursorCreatedAt, cursorId, size);
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

	@Override
	@PostMapping("/five-questions")
	public ResponseEntity<CommonResponse<ActivityResponseDTO.ActivityCreateResponse>> createFiveQuestionsActivity(
		CustomUserDetails customUserDetails, ActivityRequestDTO.FiveQuestionCreateRequest request) {
		ActivityResponseDTO.ActivityCreateResponse result = activityService
			.createFiveQuestionsActivity(customUserDetails.getId(), request);
		return new ResponseEntity<>(CommonResponse.created(result), HttpStatus.CREATED);
	}

	@Override
	@PostMapping("/role-playing")
	public ResponseEntity<CommonResponse<ActivityResponseDTO.ActivityCreateResponse>> createRolePlayingActivity(
		CustomUserDetails customUserDetails, ActivityRequestDTO.RolePlayingCreateRequest request) {
		ActivityResponseDTO.ActivityCreateResponse result = activityService
			.createRolePlayingActivity(customUserDetails.getId(), request);
		return new ResponseEntity<>(CommonResponse.created(result), HttpStatus.CREATED);
	}
}
