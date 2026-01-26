package com.greeni.api.profiles.controller.docs;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.diaries.dto.DiaryResponseDTO;
import com.greeni.api.profiles.dto.ProfileResponseDTO;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Statistics", description = "통계 API")
public interface ProfileStatisticsControllerDocs {

    @Operation(summary = "출석 및 일기 횟수 조회 API",
        description = "사용자의 출석 및 일기 횟수를 조회하는 API",
        responses = {
             @ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponseDTO.GetProfileResponse.class))),
             @ApiResponse(responseCode = "PROFILE4031", description = "해당 프로필에 접근할 권한이 없습니다."),
             @ApiResponse(responseCode = "PROFILE4041", description = "존재하지 않는 프로필입니다.")
        })
    ResponseEntity<CommonResponse<ProfileResponseDTO.GetAttendanceDiaryCountResponse>> findAttendanceDiaryCount(
        @Parameter(description = "조회할 프로필의 ID", required = true)
        @PathVariable Long profileId,
        @AuthenticationPrincipal CustomUserDetails customUserDetails
    );

    @Operation(summary = "이번 달 일기 감정 통계 조회 API",
        description = "사용자의 이번 달 일기 감정 통계를 조회하는 API",
        responses = {
            @ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다.",
                           content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiaryResponseDTO.GetMonthlyDiaryEmotionResponse.class))),
            @ApiResponse(responseCode = "PROFILE4031", description = "해당 프로필에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "PROFILE4041", description = "존재하지 않는 프로필입니다.")
        })
    ResponseEntity<CommonResponse<DiaryResponseDTO.GetMonthlyDiaryEmotionResponse>> findMonthlyDiaryEmotion(
        @Parameter(description = "조회할 프로필의 ID", required = true)
        @PathVariable Long profileId,
        @AuthenticationPrincipal CustomUserDetails customUserDetails
    );
}
