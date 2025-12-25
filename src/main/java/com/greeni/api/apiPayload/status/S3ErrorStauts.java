package com.greeni.api.apiPayload.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum S3ErrorStauts implements ErrorReason{

    IMAGE_UPLOAD_FAIL(HttpStatus.BAD_REQUEST, "S34001", "이미지 업로드가 실패했습니다"),
    NO_IMAGE(HttpStatus.BAD_REQUEST, "S34002", "이미지를 첨부해 주세요"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
