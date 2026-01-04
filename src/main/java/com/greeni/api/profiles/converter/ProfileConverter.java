package com.greeni.api.profiles.converter;

import com.greeni.api.members.domain.Member;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.dto.ProfileRequestDTO;
import com.greeni.api.profiles.dto.ProfileResponseDTO;

import java.util.List;

public class ProfileConverter {

    // entity -> 생성 응답 DTO
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

    // entity -> 수정 응답 DTO
    public static ProfileResponseDTO.UpdateProfileResponse toUpdateProfileResponseDTO(Profile profile) {
        return ProfileResponseDTO.UpdateProfileResponse.builder()
                .profileId(profile.getId())
                .profileImage(profile.getProfileImage())
                .name(profile.getName())
                .birth(profile.getBirth())
                .memberId(profile.getMember().getId())
                .createdAt(profile.getUpdatedAt())
                .build();
    }

    // entity list -> 목록 조회 응답 DTO
    public static ProfileResponseDTO.GetProfileListResponse toGetProfileListResponseDTO(List<Profile> profiles) {
        return ProfileResponseDTO.GetProfileListResponse.builder()
                .profileLists(profiles.stream()
                        .map(profile -> ProfileResponseDTO.ProfileList.builder()
                                .profileId(profile.getId())
                                .name(profile.getName())
                                .profileImage(profile.getProfileImage())
                                .build())
                        .toList())
                .build();
    }

    // entity -> 조회 응답 DTO
    public static ProfileResponseDTO.GetProfileResponse toGetProfileResponseDTO(Profile profile) {
        return ProfileResponseDTO.GetProfileResponse.builder()
                .profileId(profile.getId())
                .profileImage(profile.getProfileImage())
                .name(profile.getName())
                .birth(profile.getBirth())
                .build();
    }

    // entity -> 출석 및 일기 횟수 조회 응답 DTO
    public static ProfileResponseDTO.GetAttendanceDiaryCountResponse toAttendanceDiaryCountResponseDTO(Profile profile, int diaryCount) {
        return ProfileResponseDTO.GetAttendanceDiaryCountResponse.builder()
                .profileId(profile.getId())
                .name(profile.getName())
                .attendance(profile.getAttendance())
                .diaryCount(diaryCount)
                .build();
    }
}
