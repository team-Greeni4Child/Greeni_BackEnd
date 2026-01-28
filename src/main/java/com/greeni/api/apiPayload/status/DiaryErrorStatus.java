package com.greeni.api.apiPayload.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DiaryErrorStatus implements ErrorReason {

    FUTURE_TIME(HttpStatus.BAD_REQUEST, "DIARY4001", "미래의 연/월입니다."),
    INVALID_MONTH(HttpStatus.BAD_REQUEST, "DIARY4002", "월은 1과 12 사이의 숫자이어야 합니다."),
    INVALID_DAY(HttpStatus.BAD_REQUEST, "DIARY4003", "해당 월에 유효한 날짜가 아닙니다"),
    NOT_WRITE_DIARY(HttpStatus.BAD_REQUEST, "DIARY4004", "해당 날짜에 작성한 일기가 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
