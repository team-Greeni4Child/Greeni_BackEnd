package com.greeni.api.ai.dto;

import org.springframework.web.bind.annotation.BindParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.greeni.api.ai.domain.Purpose;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AIRequestDTO {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public record STTRequest(
		@NotNull(message = "필수 입력값입니다.")
		@Schema(description = "Indicates the feature context (roleplay, game, diary)", example = "roleplay")
		Purpose purpose,

		@NotNull(message = "필수 입력값입니다.")
		@Schema(description = "Whether to store the audio after processing")
		@JsonProperty("store_audio")
		Boolean storeAudio,

		@Schema(description = "Session Identifier", example = "039D8*&6d")
		@BindParam("session_id")
		@JsonProperty("session_id")
		String sessionId
	) {
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public record TTSRequest(
		@NotNull(message = "필수 입력값입니다.")
		@Schema(description = "Indicates the feature context (roleplay, game, diary)", example = "roleplay")
		Purpose purpose,

		@NotNull(message = "필수 입력값입니다.")
		@Schema(description = "Text to synthesize")
		String text,

		@Schema(description = "Voice id/name", example = "")
		String voice,

		@Schema(description = "Session Identifier", example = "039D8*&6d")
		@BindParam("session_id")
		@JsonProperty("session_id")
		String sessionId,

		@Schema(description = "Playback speed multiplier", example = "")
		Float speed
	) {
	}

	public record FiveQuestionsHint(
		@NotBlank(message = "필수 입력입니다.")
		@Schema(description = "Target word", example = "얼룩말")
		String answer
	) {
	}

	public record FiveQuestionsCheck(
		@NotNull(message = "필수 입력입니다.")
		@Schema(description = "음성 파일", example = "")
		MultipartFile voice,

		@NotBlank(message = "필수 입력입니다.")
		@Schema(description = "Target word", example = "얼룩말")
		String answer,

		@Schema(description = "Session Identifier", example = "039D8*&6d")
		@BindParam("session_id")
		@JsonProperty("session_id")
		String sessionId
	) {
	}

	public record FiveQuestionsCheckInner(

		@NotBlank(message = "필수 입력입니다.")
		@Schema(description = "Child utterance (from STT)", example = "얼룩말이야.")
		String utterance,

		@NotBlank(message = "필수 입력입니다.")
		@Schema(description = "Target word", example = "얼룩말")
		String answer
	) {
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public record RolePlaying(
		@NotBlank(message = "필수 입력입니다~~")
		@Schema(description = "Session Identifier", example = "039D8*&6d")
		@BindParam("session_id")
		@JsonProperty("session_id")
		String sessionId,

		@NotNull(message = "필수 입력입니다.")
		@Schema(description = "shop, teacher, friend", example = "shop")
		String role,

		@NotNull(message = "필수 입력입니다.")
		@Schema(description = "음성 파일", example = "")
		MultipartFile voice,

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

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public record RolePlayingInner(
		@NotBlank(message = "필수 입력입니다.")
		@Schema(description = "Session Identifier", example = "039D8*&6d")
		@BindParam("session_id")
		@JsonProperty("session_id")
		String sessionId,

		@Schema(description = "shop, teacher, friend", example = "shop")
		com.greeni.api.ai.domain.RolePlayingType role,

		@NotBlank(message = "필수 입력입니다.")
		@Schema(description = "아이의 음성을 텍스트로 변환한 것", example = "")
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
		@BindParam("session_id")
		@JsonProperty("session_id")
		String sessionId
	) {
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public record DiaryRequest(
			@NotNull Long profileId,
			@NotBlank(message = "필수 입력입니다~~")
			@Schema(description = "Session Identifier", example = "039D8*&6d")
			@BindParam("session_id")
			@JsonProperty("session_id")
			String session_id,

			@NotNull(message = "필수 입력입니다.")
			@Schema(description = "대화 내용을 s3에 올린 후 url", example = "")
			String voiceUrl,

			@NotBlank(message = "필수 입력입니다.")
			@Schema(description = "아이의 음성을 텍스트로 변환한 것", example = "")
			@JsonProperty("user_text")
			String user_text,

			@NotNull(message = "필수 입력입니다.")
			@Schema(description = "음성 파일", example = "")
			MultipartFile voice
	){
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public record DiaryInner(
			@NotBlank(message = "필수 입력입니다.")
			@Schema(description = "Session Identifier", example = "039D8*&6d")
			@BindParam("session_id")
			@JsonProperty("session_id")
			String sessionId,
			@NotBlank(message = "필수 입력입니다.")
			@Schema(description = "아이의 음성을 텍스트로 변환한 것", example = "")
			@JsonProperty("user_text")
			String userText
	){
	}

	public record DiaryCloseRequest(
			@NotNull Long profileId,
			@NotBlank(message = "필수 입력입니다.")
			@Schema(description = "Session Identifier", example = "039D8*&6d")
			@BindParam("session_id")
			@JsonProperty("session_id")
			String sessionId,
			@NotBlank(message = "필수 입력입니다.")
			@Schema(description = "대화 비정상 종료", example = "ended")
			String status
	){
	}

	public record DiarySummarizeRequest(
			@NotNull Long profileId,
			@Schema(description = "Session Identifier", example = "039D8*&6d")
			@BindParam("session_id")
			@JsonProperty("session_id")
			String sessionId,
			@NotNull(message = "필수 입력입니다.")
			@Schema(description = "S3에 올린 그림 일기 또는 사진", example = "")
			String imageUrl
	){
	}

	public record DiarySummarizeInnerRequest(
			@Schema(description = "Session Identifier", example = "039D8*&6d")
			@BindParam("session_id")
			@JsonProperty("session_id")
			String sessionId
	){
	}

}
