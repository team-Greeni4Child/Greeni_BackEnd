package com.greeni.api.profiles.docs;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.profiles.dto.ProfileRequestDTO;
import com.greeni.api.profiles.dto.ProfileResponseDTO;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Profile", description = "프로필 CRUD API")
public interface ProfileControllerDocs {

    @Operation(summary = "프로필 생성 API",
            description = "로그인한 사용자의 프로필을 생성하는 API",
            responses = {
                    @ApiResponse(responseCode = "COMMON201", description = "리소스를 생성했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponseDTO.CreateProfileResponse.class))),
                    @ApiResponse(responseCode = "MEMBER4041", description = "존재하지 않는 회원입니다.")

            })
    ResponseEntity<CommonResponse<ProfileResponseDTO.CreateProfileResponse>> addProfile(
            @RequestBody @Valid ProfileRequestDTO.CreateProfileRequest dto,
            @AuthenticationPrincipal CustomUserDetails customUsersDetails
    );
}
