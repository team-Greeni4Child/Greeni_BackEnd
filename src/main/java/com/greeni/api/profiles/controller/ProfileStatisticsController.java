package com.greeni.api.profiles.controller;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.diaries.dto.DiaryResponseDTO;
import com.greeni.api.diaries.service.DiaryService;
import com.greeni.api.profiles.controller.docs.ProfileStatisticsControllerDocs;
import com.greeni.api.profiles.dto.ProfileResponseDTO;
import com.greeni.api.profiles.service.ProfileService;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class ProfileStatisticsController implements ProfileStatisticsControllerDocs {

    private final ProfileService profileService;
    private final DiaryService diaryService;

    // 출석 및 일기 횟수 조회
    @GetMapping("/profiles/{profileId}/count")
    public ResponseEntity<CommonResponse<ProfileResponseDTO.GetAttendanceDiaryCountResponse>> findAttendanceDiaryCount(
            @PathVariable Long profileId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        ProfileResponseDTO.GetAttendanceDiaryCountResponse result = profileService.getAttendanceDiaryCount(customUserDetails.getId(), profileId);
        return new ResponseEntity<>(CommonResponse.onSuccess(result), HttpStatus.OK);
    }

    // 이번 달 일기 감정 통계 조회
    @GetMapping("/diaries/{profileId}/emotion")
    public ResponseEntity<CommonResponse<DiaryResponseDTO.GetMonthlyDiaryEmotionResponse>> findMonthlyDiaryEmotion(
            @PathVariable Long profileId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        DiaryResponseDTO.GetMonthlyDiaryEmotionResponse result = diaryService.getMonthlyDiaryEmotion(customUserDetails.getId(), profileId);
        return new ResponseEntity<>(CommonResponse.onSuccess(result), HttpStatus.OK);
    }
}
