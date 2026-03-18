package com.greeni.api.ai.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public class AIResponseDTO {

	@Builder
	public record STTResponse(
		String text,
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
	public record HintData(
		@Schema(description = "힌트 텍스트")
		String hintText,
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
		List<HintData> hints,
		String sessionId
	) {
	}

	@Builder
	public record FiveQuestionsCheckResponse(
		String answerCheckVoiceUrl,
		String sessionId
	) {
		public static FiveQuestionsCheckResponse of(Boolean correct, String sessionId) {
			return FiveQuestionsCheckResponse.builder()
				.answerCheckVoiceUrl(correct ? "" : "")
				.sessionId(sessionId)
				.build();
		}
	}

	@Builder
	public record RolePlayingResponse(
		String sessionId,
		String base64Voice,
		Integer turn
	) {
		public static RolePlayingResponse of(String sessionId, String base64Voice, Integer turn) {
			return RolePlayingResponse.builder()
				.sessionId(sessionId)
				.base64Voice(base64Voice)
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
