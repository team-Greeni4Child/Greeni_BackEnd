package com.greeni.api.diaries.service;

import com.greeni.api.diaries.dto.DiaryResponseDTO;

public interface DiaryService {

    DiaryResponseDTO.MonthDiaryListDTO getMonthDiaryList(int year, int month, Long memberId, Long profileId);
}
