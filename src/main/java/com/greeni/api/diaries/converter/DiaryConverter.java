package com.greeni.api.diaries.converter;

import com.greeni.api.diaries.dto.DiaryResponseDTO;

public class DiaryConverter {

    // entity -> 오늘의 일기 키워드 조회 응답 DTO
    public static DiaryResponseDTO.GetTodayDiaryKeywordResponse toTodayDiaryKeywordResponseDTO(Long profileId, String keyword) {
        return DiaryResponseDTO.GetTodayDiaryKeywordResponse.builder()
                .profileId(profileId)
                .keyword(keyword)
                .build();
    }
}
