package com.greeni.api.diaries.dto;

import com.greeni.api.diaries.domain.enums.Emotion;
import lombok.Builder;

import java.util.List;

public class DiaryResponseDTO {

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
}
