package com.greeni.api.diaries.controller.docs;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.diaries.dto.DiaryRequestDTO;
import com.greeni.api.diaries.dto.DiaryResponseDTO;
import com.greeni.api.profiles.dto.ProfileResponseDTO;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Diary", description = "일기 CRU API")
public interface DiaryControllerDocs {

    @Operation(summary = "월별 일기 목록 조회 API",
            description = "월별 일기의 목록을 조회하는 API",
            responses = {
                    @ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiaryResponseDTO.MonthDiaryListDTO.class))),
                    @ApiResponse(responseCode = "PROFILE4041", description = "존재하지 않는 프로필입니다."),
                    @ApiResponse(responseCode = "PROFILE4031", description = "해당 프로필에 접근할 권한이 없습니다."),
                    @ApiResponse(responseCode = "DIARY4001", description = "미래의 연/월입니다."),
                    @ApiResponse(responseCode = "DIARY4002", description = "월은 1과 12 사이의 숫자이어야 합니다.")

            })
    ResponseEntity<CommonResponse<DiaryResponseDTO.MonthDiaryListDTO>> findMonthDiaryList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                                                 @RequestParam int year,
                                                                                                 @RequestParam int month,
                                                                                                 @RequestParam Long profileId);


    @Operation(summary = "일별 일기 상세 조회API",
            description = "특정 날짜의 일기를 상세하게 조회하는 API",
            responses = {
                    @ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiaryResponseDTO.MonthDiaryListDTO.class))),
                    @ApiResponse(responseCode = "PROFILE4041", description = "존재하지 않는 프로필입니다."),
                    @ApiResponse(responseCode = "PROFILE4031", description = "해당 프로필에 접근할 권한이 없습니다."),
                    @ApiResponse(responseCode = "DIARY4001", description = "미래의 연/월입니다."),
                    @ApiResponse(responseCode = "DIARY4002", description = "월은 1과 12 사이의 숫자이어야 합니다."),
                    @ApiResponse(responseCode = "DIARY4003", description = "해당 월에 유효한 날짜가 아닙니다"),
                    @ApiResponse(responseCode = "DIARY4004", description = "해당 날짜에 작성한 일기가 없습니다.")

            })
    ResponseEntity<CommonResponse<DiaryResponseDTO.DailyDiaryDTO>> findDailyDiary(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                                  @RequestParam int year,
                                                                                  @RequestParam int month,
                                                                                  @RequestParam int day,
                                                                                  @RequestParam Long profileId);

    @Operation(summary = "일기 음성 조회 API",
            description = "특정 날짜의 일기의 음성을 조회하는 API",
            responses = {
                    @ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiaryResponseDTO.DiaryVoiceListDTO.class))),
                    @ApiResponse(responseCode = "PROFILE4041", description = "존재하지 않는 프로필입니다."),
                    @ApiResponse(responseCode = "PROFILE4031", description = "해당 프로필에 접근할 권한이 없습니다."),
                    @ApiResponse(responseCode = "DIARY4001", description = "미래의 연/월입니다."),
                    @ApiResponse(responseCode = "DIARY4002", description = "월은 1과 12 사이의 숫자이어야 합니다."),
                    @ApiResponse(responseCode = "DIARY4003", description = "해당 월에 유효한 날짜가 아닙니다"),
                    @ApiResponse(responseCode = "DIARY4004", description = "해당 날짜에 작성한 일기가 없습니다.")

            })
    ResponseEntity<CommonResponse<DiaryResponseDTO.DiaryVoiceListDTO>> findDailyDiaryVoice(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                                           @RequestParam int year,
                                                                                           @RequestParam int month,
                                                                                           @RequestParam int day,
                                                                                           @RequestParam Long profileId);

    @Operation(summary = "일기 저장 API",
            description = "오늘의 일기를 저장하는 API",
            responses = {
                    @ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiaryResponseDTO.CreateDiaryDTO.class))),
                    @ApiResponse(responseCode = "PROFILE4041", description = "존재하지 않는 프로필입니다."),
                    @ApiResponse(responseCode = "PROFILE4031", description = "해당 프로필에 접근할 권한이 없습니다."),
                    @ApiResponse(responseCode = "BADGE4041", description = "해당 배지를 찾을 수 없습니다.")
            })
    ResponseEntity<CommonResponse<DiaryResponseDTO.CreateDiaryDTO>> makeDiary(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                              @RequestBody @Valid DiaryRequestDTO.DiarySaveDTO request);
}
