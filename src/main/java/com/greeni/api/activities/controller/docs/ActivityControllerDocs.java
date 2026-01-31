package com.greeni.api.activities.controller.docs;

import com.greeni.api.activities.dto.ActivityResponseDTO;
import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Activity", description = "활동요약 CR API")
public interface ActivityControllerDocs {

    @Operation(summary = "활동 요약 목록 조회 API",
            description = "활동 요약 목록을 조회하는 API",
            responses = {
                    @ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityResponseDTO.GetActivitySummaryListResponse.class))),
                    @ApiResponse(responseCode = "PROFILE4031", description = "해당 프로필에 접근할 권한이 없습니다."),
                    @ApiResponse(responseCode = "PROFILE4041", description = "존재하지 않는 프로필입니다.")
            })
    ResponseEntity<CommonResponse<ActivityResponseDTO.GetActivitySummaryListResponse>> findActivitySummaryList(
            @RequestParam Long profileId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );
}
