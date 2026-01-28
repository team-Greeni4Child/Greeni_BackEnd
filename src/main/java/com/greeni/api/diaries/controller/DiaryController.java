package com.greeni.api.diaries.controller;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.diaries.dto.DiaryRequestDTO;
import com.greeni.api.diaries.dto.DiaryResponseDTO;
import com.greeni.api.diaries.service.DiaryService;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;
import jakarta.validation.Valid;
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
        DiaryResponseDTO.MonthDiaryListDTO result = diaryService.getMonthDiaryList(year, month, customUserDetails.getId(),profileId);
        return new ResponseEntity<>(CommonResponse.onSuccess(result), HttpStatus.OK);
    }
}
