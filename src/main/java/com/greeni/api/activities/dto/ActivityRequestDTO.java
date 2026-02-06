package com.greeni.api.activities.dto;

import com.greeni.api.activities.domain.enums.RoleName;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ActivityRequestDTO {

	public record FiveQuestionCreateRequest(
		@NotNull @Schema(description = "프로필 ID", example = "0")
		Long profileId,
		@NotNull @Schema(description = "다섯고개에서 정답을 맞히기까지 걸린 턴 수 / 정답을 맞히지 못했다면 0") @Min(0) @Max(5)
		Long count
	) {
	}

	public record RolePlayingCreateRequest(
		@NotNull @Schema(description = "프로필 ID", example = "0")
		Long profileId,
		@NotNull @Schema(description = "역할놀이에서 맡은 역할")
		RoleName roleName
	) {
	}
}
