package com.greeni.api.profiles.dto;

import java.time.LocalDate;

public class ProfileRequestDTO {

    public record CreateProfileRequest(
            String profileImage,
            String name,
            LocalDate birth
    ) {}
}
