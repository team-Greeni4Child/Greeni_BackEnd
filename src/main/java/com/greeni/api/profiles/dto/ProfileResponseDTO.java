package com.greeni.api.profiles.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProfileResponseDTO {

    @Builder
    public record CreateProfileResponse (
            Long profileId,
            String profileImage,
            String name,
            LocalDate birth,
            Long memberId,
            LocalDateTime createdAt
    ) {}
}
