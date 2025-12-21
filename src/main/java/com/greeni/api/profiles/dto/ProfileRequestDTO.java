package com.greeni.api.profiles.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ProfileRequestDTO {

    public record CreateProfileRequest(
            @Schema(description = "프로필 이미지", example = "greeni.jpg")
            String profileImage,

            @NotBlank(message = "이름은 필수 입력입니다.")
            @Size(min = 1, max = 20, message = "이름은 1~20자 사이여야 합니다.")
            @Schema(description = "사용자 이름", example = "그리니")
            String name,

            @Past(message = "생년원일은 과거 날짜여야 합니다.")
            @NotNull(message = "생년원일은 필수 입력입니다.")
            @Schema(description = "사용자 생년월일 (yyyy-MM-dd 형식)", example = "2025-11-20")
            LocalDate birth
    ) {}

    public record UpdateProfileRequest(
            @Schema(description = "프로필 이미지", example = "greeni.jpg")
            String profileImage,

            @NotBlank(message = "이름은 필수 입력입니다.")
            @Size(min = 1, max = 20, message = "이름은 1~20자 사이여야 합니다.")
            @Schema(description = "수정할 사용자 이름", example = "개굴개굴")
            String name,

            @Past(message = "생년원일은 과거 날짜여야 합니다.")
            @NotNull(message = "생년원일은 필수 입력입니다.")
            @Schema(description = "수정할 사용자 생년월일 (yyyy-MM-dd 형식)", example = "2024-11-20")
            LocalDate birth
    ) {}
}
