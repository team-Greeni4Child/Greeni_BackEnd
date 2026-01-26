package com.greeni.api.apiPayload.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DiaryErrorStatus implements ErrorReason {

    DIARY_NOT_FOUND_TODAY(HttpStatus.NOT_FOUND, "DIARY4041", "오늘 작성된 일기가 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
