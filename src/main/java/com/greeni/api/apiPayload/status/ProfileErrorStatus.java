package com.greeni.api.apiPayload.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProfileErrorStatus implements ErrorReason {

    INVALID_PROFILE_DATA(HttpStatus.BAD_REQUEST, "PROFILE4001", "프로필 정보가 유효하지 않습니다."),
    PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "PROFILE4041", "존재하지 않는 프로필입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
