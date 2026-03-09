package com.greeni.api.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.greeni.api.ai.domain.RolePlayingType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AIRequestDTO {

	public record FiveQuestionsHint(
		@NotBlank(message = "필수 입력입니다.")
		@Schema(description = "Target word", example = "얼룩말")
		String answer
	) {
	}

	public record FiveQuestionsCheck(
		@NotBlank(message = "필수 입력입니다.")
		@Schema(description = "Child utterance (from STT)", example = "얼룩말이야.")
		String utterance,

		@NotBlank(message = "필수 입력입니다.")
		@Schema(description = "Target word", example = "얼룩말")
		String answer
	) {
	}

	public record RolePlaying(
		@NotBlank(message = "필수 입력입니다.")
		@Schema(description = "Session Identifier", example = "039D8*&6d")
		@JsonProperty("session_id")
		String sessionId,

		@NotNull(message = "필수 입력입니다.")
		@Schema(description = "SHOP, TEACHER, FRIEND", example = "shop")
		RolePlayingType role,

		@NotBlank(message = "필수 입력입니다.")
		@Schema(description = "사용자 입력", example = "고구마 팔아요?")
		@JsonProperty("user_text")
		String userText,

		@Schema(description = "창의성 조절", example = "0.7")
		Float temperature,

		@Schema(description = "일관성 조절", example = "1.0")
		@JsonProperty("top_p")
		Float topP,

		@Schema(description = "토큰 수 제한", example = "256")
		@JsonProperty("max_tokens")
		Integer maxTokens
	) {
	}

	public record RolePlayingEnd(
		@NotBlank(message = "필수 입력입니다.")
		@Schema(description = "Session Identifier", example = "039D8*&6d")
		@JsonProperty("session_id")
		String sessionId
	) {
	}
}
