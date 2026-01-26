package com.greeni.api.diaries.dto;

import lombok.Builder;

public class DiaryResponseDTO {

    @Builder
    public record GetTodayDiaryKeywordResponse(
            Long profileId,
            String keyword
    ) {}
}
