package com.greeni.api.diaries.converter;

import com.greeni.api.diaries.domain.Diary;
import com.greeni.api.diaries.dto.DiaryResponseDTO;

import java.util.List;

public class DiaryConverter {

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

    public static DiaryResponseDTO.DailyDiaryDTO toDailyDiaryDTO(Long profileId, Diary diary) {
        return DiaryResponseDTO.DailyDiaryDTO.builder()
                .profileId(profileId)
                .diaryImage(diary.getDiaryImage())
                .keyword(diary.getKeyword())
                .summary(diary.getSummary())
                .emotion(diary.getEmotion())
                .build();
    }
}
