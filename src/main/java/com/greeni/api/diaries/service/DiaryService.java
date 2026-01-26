package com.greeni.api.diaries.service;

import com.greeni.api.diaries.dto.DiaryResponseDTO;

public interface DiaryService {

    // 이번 달 일기 감정 통계 조회
    DiaryResponseDTO.GetMonthlyDiaryEmotionResponse getMonthlyDiaryEmotion(Long memberId, Long profileId);
}
