package com.greeni.api.profiles.converter;

import com.greeni.api.members.domain.Member;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.dto.ProfileRequestDTO;
import com.greeni.api.profiles.dto.ProfileResponseDTO;

public class ProfileConverter {

    // entity -> DTO
    public static ProfileResponseDTO.CreateProfileResponse toCreateProfileResponseDTO(Profile profile) {
        return ProfileResponseDTO.CreateProfileResponse.builder()
                .profileId(profile.getId())
                .profileImage(profile.getProfileImage())
                .name(profile.getName())
                .birth(profile.getBirth())
                .memberId(profile.getMember().getId())
                .createdAt(profile.getCreatedAt())
                .build();
    }

    // DTO -> entity
    public static Profile toProfile(Member member, ProfileRequestDTO.CreateProfileRequest dto) {
        return Profile.builder()
                .profileImage(dto.profileImage())
                .name(dto.name())
                .birth(dto.birth())
                .member(member)
                .build();
    }
}
