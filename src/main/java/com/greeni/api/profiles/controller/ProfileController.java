package com.greeni.api.profiles.controller;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.profiles.dto.ProfileRequestDTO;
import com.greeni.api.profiles.dto.ProfileResponseDTO;
import com.greeni.api.profiles.service.ProfileService;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.greeni.api.profiles.docs.ProfileControllerDocs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController implements ProfileControllerDocs {

    private final ProfileService profileService;

    // 프로필 생성
    @PostMapping
    public ResponseEntity<CommonResponse<ProfileResponseDTO.CreateProfileResponse>> addProfile(
            @RequestBody @Valid ProfileRequestDTO.CreateProfileRequest dto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        ProfileResponseDTO.CreateProfileResponse result = profileService.createProfile(customUserDetails.getId(), dto);
        return new ResponseEntity<>(CommonResponse.created(result), HttpStatus.CREATED);
    }

    // 프로필 수정
    @PatchMapping("/{profileId}")
    public ResponseEntity<CommonResponse<ProfileResponseDTO.UpdateProfileResponse>> changeProfile(
            @PathVariable Long profileId,
            @RequestBody @Valid ProfileRequestDTO.UpdateProfileRequest dto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        ProfileResponseDTO.UpdateProfileResponse result = profileService.updateProfile(customUserDetails.getId(), profileId, dto);
        return new ResponseEntity<>(CommonResponse.onSuccess(result), HttpStatus.OK);
    }
}
