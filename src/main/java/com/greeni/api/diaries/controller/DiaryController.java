package com.greeni.api.diaries.controller;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.diaries.dto.DiaryRequestDTO;
import com.greeni.api.diaries.dto.DiaryResponseDTO;
import com.greeni.api.diaries.service.DiaryService;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.greeni.api.diaries.controller.docs.DiaryControllerDocs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController implements DiaryControllerDocs {

    private final DiaryService diaryService;

    // 월별 일기 조회
    @GetMapping("/month")
    public ResponseEntity<CommonResponse<DiaryResponseDTO.MonthDiaryListDTO>> findMonthDiaryList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                                                 @RequestParam int year,
                                                                                                 @RequestParam int month,
                                                                                                 @RequestParam Long profileId){
        DiaryResponseDTO.MonthDiaryListDTO result = diaryService.getMonthDiaryList(year, month, customUserDetails.getId(), profileId);
        return new ResponseEntity<>(CommonResponse.onSuccess(result), HttpStatus.OK);
    }

    // 일별 상세 일기 조회
    @GetMapping("/day")
    public ResponseEntity<CommonResponse<DiaryResponseDTO.DailyDiaryDTO>> findDailyDiary(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                            @RequestParam int year,
                                                            @RequestParam int month,
                                                            @RequestParam int day,
                                                            @RequestParam Long profileId){
        DiaryResponseDTO.DailyDiaryDTO result = diaryService.getDailyDiary(year, month, day, customUserDetails.getId(), profileId);
        return new ResponseEntity<>(CommonResponse.onSuccess(result), HttpStatus.OK);
    }

    @GetMapping("/day/voice")
    public ResponseEntity<CommonResponse<DiaryResponseDTO.DiaryVoiceListDTO>> findDailyDiaryVoice(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                 @RequestParam int year,
                                                                 @RequestParam int month,
                                                                 @RequestParam int day,
                                                                 @RequestParam Long profileId){
        DiaryResponseDTO.DiaryVoiceListDTO result = diaryService.getDiaryVoice(year, month, day, customUserDetails.getId(), profileId);
        return new ResponseEntity<>(CommonResponse.onSuccess(result), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<CommonResponse<DiaryResponseDTO.CreateDiaryDTO>> makeDiary(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                                     @RequestBody @Valid DiaryRequestDTO.DiarySaveDTO request){
        DiaryResponseDTO.CreateDiaryDTO result = diaryService.createDiary(customUserDetails.getId(), request);
        return new ResponseEntity<>(CommonResponse.onSuccess(result), HttpStatus.OK);
    }

    @PostMapping("/voice")
    public ResponseEntity<CommonResponse<?>> getVoice(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                      @RequestBody @Valid DiaryRequestDTO.DiaryUrlDTO request){
        diaryService.getVoiceUrl(customUserDetails.getId(), request);
        return new ResponseEntity<>(CommonResponse.onSuccess(null), HttpStatus.OK);
    }
}
