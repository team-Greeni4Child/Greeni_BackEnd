package com.greeni.api.profiles.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Builder
    public record UpdateProfileResponse (
            Long profileId,
            String profileImage,
            String name,
            LocalDate birth,
            Long memberId,
            LocalDateTime createdAt
    ) {}

    @Builder
    public record GetProfileListResponse (
            List<ProfileList> profileLists
    ) {}

    @Builder
    public record ProfileList (
            Long profileId,
            String name,
            String profileImage
    ) {}
}
