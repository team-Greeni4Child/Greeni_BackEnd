package com.greeni.api.ai.dto;

import java.util.List;

import lombok.Builder;

public class AIResponseDTO {

	@Builder
	public record FiveQuestionsHintResponse(
		List<String> hints
	) {
		public static FiveQuestionsHintResponse of(List<String> hints) {
			return FiveQuestionsHintResponse.builder()
				.hints(hints)
				.build();
		}
	}

	@Builder
	public record FiveQuestionsCheckResponse(
		Boolean correct
	) {
		public static FiveQuestionsCheckResponse of(Boolean correct) {
			return FiveQuestionsCheckResponse.builder()
				.correct(correct)
				.build();
		}
	}

	@Builder
	public record RolePlayingResponse(
		String sessionId,
		String reply,
		Integer turn
	) {
		public static RolePlayingResponse of(String sessionId, String reply, Integer turn) {
			return RolePlayingResponse.builder()
				.sessionId(sessionId)
				.reply(reply)
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
