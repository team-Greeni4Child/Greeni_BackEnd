package com.greeni.api.apiPayload.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 회원 관련 응답
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "MEMBER4001", "이미 존재하는 메일입니다"),
    EXPIRED_CODE(HttpStatus.BAD_REQUEST, "MEMBER4002", "이메일 인증코드가 만료되었습니다"),
    WRONG_CODE(HttpStatus.BAD_REQUEST, "MEMBER4003", "이메일 인증코드가 올바르지 않습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}