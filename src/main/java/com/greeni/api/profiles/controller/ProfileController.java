package com.greeni.api.profiles.controller;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.profiles.dto.ProfileRequestDTO;
import com.greeni.api.profiles.dto.ProfileResponseDTO;
import com.greeni.api.profiles.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            @RequestBody @Valid ProfileRequestDTO.CreateProfileRequest dto
    ) {
        ProfileResponseDTO.CreateProfileResponse result = profileService.createProfile(dto);
        return new ResponseEntity<>(CommonResponse.created(result), HttpStatus.CREATED);
    }
}
