package com.greeni.api.diaries.dto;

import com.greeni.api.diaries.domain.enums.Emotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class DiaryResponseDTO {

    @Builder
    public record GetTodayDiaryKeywordResponse(
            Long profileId,
            String keyword
    ) {}

    @Builder
    public record GetMonthlyDiaryEmotionResponse(
            Long profileId,
            List<MonthlyEmotionStat> stats
    ) {}

    @Builder
    public record MonthlyEmotionStat(
            Emotion emotion,
            long count
    ) {}

    @Builder
    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class MonthDiaryListDTO{
        Long profileId;
        List<MonthDiaryDTO> diaries;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class MonthDiaryDTO{
        Emotion emotion;
        int day;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class DailyDiaryDTO{
        Long profileId;
        String diaryImage;
        String summary;
        Emotion emotion;
        String keyword;
    }
}
