package com.greeni.api.s3.docs;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.s3.dto.S3ResponseDTO;
import com.greeni.api.security.auth.dto.AuthResponseDTO;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "S3", description = "이미지 업로드 API")
public interface S3ControllerDocs {

    @Operation(
            summary = "이미지 업로드 API",
            description = "그림일기의 이미지를 업로드하는 url을 생성하는 API",
            responses = {
                    @ApiResponse(responseCode = "COMMON200", description = "요청이 성공했습니다..",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = S3ResponseDTO.GetS3UrlDTO.class)))
            }
    )
    CommonResponse<S3ResponseDTO.GetS3UrlDTO> uploadFile(@AuthenticationPrincipal CustomUserDetails customUserDetails, String file);

}
