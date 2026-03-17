package com.greeni.api.apiPayload.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum TermErrorStatus implements ErrorReason {
    TERM_NOT_FOUND(HttpStatus.NOT_FOUND, "TERM4001", "존재하는 이용약관이 아닙니다"),
    MISSING_NECESSARY_TERM(HttpStatus.NOT_FOUND, "TERM4002", "필수 동의 항목을 동의하지 않으셨습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
