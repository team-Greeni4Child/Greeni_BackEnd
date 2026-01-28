package com.greeni.api.diaries.converter;

import com.greeni.api.diaries.domain.Diary;
import com.greeni.api.diaries.domain.enums.Emotion;
import com.greeni.api.diaries.dto.DiaryResponseDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DiaryConverter {

    // entity -> 오늘의 일기 키워드 조회 응답 DTO
    public static DiaryResponseDTO.GetTodayDiaryKeywordResponse toTodayDiaryKeywordResponseDTO(Long profileId, String keyword) {
        return DiaryResponseDTO.GetTodayDiaryKeywordResponse.builder()
                .profileId(profileId)
                .keyword(keyword)
                .build();
    }

    // entity -> 이번 달 일기 감정 통계 조회 응답 DTO
    public static DiaryResponseDTO.GetMonthlyDiaryEmotionResponse toMonthlyDiaryEmotionResponseDTO(Long profileId, List<Diary> diaries) {
        Map<Emotion, Long> counts = diaries.stream()
                .collect(Collectors.groupingBy(Diary::getEmotion, Collectors.counting()));

        List<DiaryResponseDTO.MonthlyEmotionStat> stats = counts.entrySet().stream()
                .map(entry -> DiaryResponseDTO.MonthlyEmotionStat.builder()
                        .emotion(entry.getKey())
                        .count(entry.getValue())
                        .build())
                .toList();

        return DiaryResponseDTO.GetMonthlyDiaryEmotionResponse.builder()
                .profileId(profileId)
                .stats(stats)
                .build();
    }

    public static DiaryResponseDTO.MonthDiaryListDTO toMonthDiaryListDTO(Long profileId, List<Diary> diaryList){
        List<DiaryResponseDTO.MonthDiaryDTO> result = diaryList.stream()
                .map(diary -> DiaryResponseDTO.MonthDiaryDTO.builder()
                        .day(diary.getCreatedAt().getDayOfMonth())
                        .emotion(diary.getEmotion())
                        .build()
                )
                .toList();

        return DiaryResponseDTO.MonthDiaryListDTO.builder()
                .profileId(profileId)
                .diaries(result)
                .build();
    }
}
