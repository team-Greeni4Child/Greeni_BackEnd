package com.greeni.api.diaries.service;

import com.greeni.api.diaries.dto.DiaryResponseDTO;

public interface DiaryService {

    // 오늘의 일기 키워드 조회
    DiaryResponseDTO.GetTodayDiaryKeywordResponse getTodayDiaryKeyword(Long memberId, Long profileId);
}
