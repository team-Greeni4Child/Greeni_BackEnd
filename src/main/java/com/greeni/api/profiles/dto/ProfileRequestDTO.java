package com.greeni.api.profiles.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ProfileRequestDTO {

    public record CreateProfileRequest(
            String profileImage,

            @NotBlank(message = "이름은 필수 입력입니다.")
            @Size(min = 1, max = 20, message = "이름은 1~20자 사이여야 합니다.")
            @Schema(description = "사용자 이름", example = "그리니")
            String name,

            @NotNull(message = "생년원일은 필수 입력입니다.")
            @Schema(description = "사용자 생년월일", example = "2025-11-20")
            LocalDate birth
    ) {}
}
