package com.greeni.api.diaries.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class DiaryRequestDTO {

    @Builder
    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class DiarySaveDTO{

        @NotNull
        Long profileId;

        @NotNull
        Long sessionId;

        String imageUrl;

        @NotNull
        String summary;

        @NotNull
        List<VoiceDTO> voiceList;

        @NotNull
        String emotion;

        @NotNull
        String keyword;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class VoiceDTO{
        @NotNull
        String url;

        @NotNull
        String role;
    }

}
