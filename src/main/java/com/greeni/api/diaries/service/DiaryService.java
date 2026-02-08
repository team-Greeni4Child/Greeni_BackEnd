package com.greeni.api.diaries.service;

import com.greeni.api.diaries.dto.DiaryResponseDTO;
import com.greeni.api.profiles.domain.Profile;

public interface DiaryService {

    // 오늘의 일기 키워드 조회
    DiaryResponseDTO.GetTodayDiaryKeywordResponse getTodayDiaryKeyword(Long memberId, Long profileId);

    // 이번 달 일기 감정 통계 조회
    DiaryResponseDTO.GetMonthlyDiaryEmotionResponse getMonthlyDiaryEmotion(Long memberId, Long profileId);

    DiaryResponseDTO.MonthDiaryListDTO getMonthDiaryList(int year, int month, Long memberId, Long profileId);

    DiaryResponseDTO.DailyDiaryDTO getDailyDiary(int year, int month, int day, Long id, Long profileId);

    DiaryResponseDTO.DiaryVoiceListDTO getDiaryVoice(int year, int month, int day, Long memberId, Long profileId);

	Profile findProfileAndValidate(Long profileId, Long memberId);
}
