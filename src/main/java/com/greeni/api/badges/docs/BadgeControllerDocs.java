package com.greeni.api.badges.docs;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.badges.dto.BadgeResponseDTO;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Badge", description = "배지 CRUD API")
public interface BadgeControllerDocs {

    @Operation(summary = "배지 목록 조회 API",
            description = "로그인한 사용자의 배지 목록을 조회하는 API",
            responses = {
                    @ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadgeResponseDTO.GetBadgeListResponse.class))),
                    @ApiResponse(responseCode = "PROFILE4031", description = "해당 프로필에 접근할 권한이 없습니다."),
                    @ApiResponse(responseCode = "PROFILE4041", description = "존재하지 않는 프로필입니다.")
            })
    ResponseEntity<CommonResponse<BadgeResponseDTO.GetBadgeListResponse>> findBadgeList(
            @Parameter(description = "조회할 프로필 ID", required = true)
            @RequestParam Long profileId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );
}
