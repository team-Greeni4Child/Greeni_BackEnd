package com.greeni.api.profiles.docs;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.profiles.dto.ProfileRequestDTO;
import com.greeni.api.profiles.dto.ProfileResponseDTO;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Profile", description = "프로필 CRUD API")
public interface ProfileControllerDocs {

    @Operation(summary = "프로필 생성 API",
            description = "로그인한 사용자의 프로필을 생성하는 API",
            responses = {
                    @ApiResponse(responseCode = "COMMON201", description = "리소스를 생성했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponseDTO.CreateProfileResponse.class))),
                    @ApiResponse(responseCode = "PROFILE4002", description = "프로필은 최대 6개까지 생성할 수 있습니다."),
                    @ApiResponse(responseCode = "MEMBER4041", description = "존재하지 않는 회원입니다.")

            })
    ResponseEntity<CommonResponse<ProfileResponseDTO.CreateProfileResponse>> addProfile(
            @RequestBody @Valid ProfileRequestDTO.CreateProfileRequest dto,
            @AuthenticationPrincipal CustomUserDetails customUsersDetails
    );

    @Operation(summary = "프로필 수정 API",
            description = "사용자의 프로필을 수정하는 API",
            responses = {
                    @ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponseDTO.UpdateProfileResponse.class))),
                    @ApiResponse(responseCode = "PROFILE4041", description = "존재하지 않는 프로필입니다.")
            })
    ResponseEntity<CommonResponse<ProfileResponseDTO.UpdateProfileResponse>> changeProfile(
            @Parameter(description = "수정할 프로필의 ID", required = true)
            @PathVariable Long profileId,
            @RequestBody @Valid ProfileRequestDTO.UpdateProfileRequest dto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );

    @Operation(summary = "프로필 삭제 API",
            description = "사용자의 프로필을 삭제하는 API",
            responses = {
                    @ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommonResponse.class))),
                    @ApiResponse(responseCode = "PROFILE4031", description = "해당 프로필에 접근할 권한이 없습니다.")
            })
    ResponseEntity<CommonResponse<Void>> removeProfile(
            @Parameter(description = "삭제할 프로필의 ID", required = true)
            @PathVariable Long profileId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );

    @Operation(summary = "프로필 목록 조회 API",
            description = "사용자의 프로필 목록을 조회하는 API",
            responses = {
                    @ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponseDTO.GetProfileListResponse.class))),
            })
    ResponseEntity<CommonResponse<ProfileResponseDTO.GetProfileListResponse>> findProfileList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );
}
