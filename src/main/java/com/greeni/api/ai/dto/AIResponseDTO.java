package com.greeni.api.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

public class AIResponseDTO {

    @Builder
    public record STTResponse(
            String text,
            @JsonProperty("audio_url")
            String audioUrl
    ) {
        public static STTResponse of(String text, String audioUrl) {
            return STTResponse.builder()
                    .text(text)
                    .audioUrl(audioUrl)
                    .build();
        }
    }

    @Builder
    public record TTSResponse(
            byte[] audioContent
    ) {
        public static TTSResponse of(byte[] audioContent) {
            return TTSResponse.builder()
                    .audioContent(audioContent)
                    .build();
        }
    }

    @Builder
    public record FiveQuestionHintVoice(
            List<String> hintLists
    ) {
        public static FiveQuestionHintVoice of(List<String> hintLists) {
            return FiveQuestionHintVoice.builder()
                    .hintLists(hintLists)
                    .build();
        }
    }

    @Builder
    public record TextVoiceData(
            @Schema(description = "힌트 텍스트")
            String text,
            @Schema(description = "Base64로 인코딩된 TTS 음성 파일")
            String audioBase64
    ) {
    }

    @Builder
    public record FiveQuestionsHintText(
            List<String> hints
    ) {
    }

    @Builder
    public record FiveQuestionsHintResponse(
            List<TextVoiceData> hints,
            String sessionId
    ) {
    }

    @Builder
    public record FiveQuestionsHintInner(
            String audioContent,
            String audioUrl
    ) {
    }

    @Builder
    public record FiveQuestionsCheckAnswer(
            Boolean correct
    ) {
    }

    @Builder
    public record FiveQuestionsCheckResponse(
            Boolean correct,
            String sessionId
    ) {
        public static FiveQuestionsCheckResponse of(Boolean correct, String sessionId) {
            return FiveQuestionsCheckResponse.builder()
                    .correct(correct)
                    .sessionId(sessionId)
                    .build();
        }
    }

    @Builder
    public record RolePlayingInnerResponse(
            String sessionId,
            String reply,
            Integer turn
    ) {
        public static RolePlayingInnerResponse of(String sessionId, String reply, Integer turn) {
            return RolePlayingInnerResponse.builder()
                    .sessionId(sessionId)
                    .reply(reply)
                    .turn(turn)
                    .build();
        }
    }

    @Builder
    public record RolePlayingResponse(
            String sessionId,
            String base64Voice,
            String text,
            Integer turn
    ) {
        public static RolePlayingResponse of(String sessionId, String base64Voice, String text, Integer turn) {
            return RolePlayingResponse.builder()
                    .sessionId(sessionId)
                    .base64Voice(base64Voice)
                    .text(text)
                    .turn(turn)
                    .build();
        }
    }

    @Builder
    public record RolePlayingEndResponse(
            String sessionId
    ) {
        public static RolePlayingEndResponse of(String sessionId) {
            return RolePlayingEndResponse.builder()
                    .sessionId(sessionId)
                    .build();
        }
    }
}
