package com.greeni.api.apiPayload.status;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorStatus implements ErrorReason {

	EXIST_EMAIL(HttpStatus.BAD_REQUEST, "MEMBER4001", "이미 존재하는 메일입니다"),
	EXPIRED_CODE(HttpStatus.BAD_REQUEST, "MEMBER4002", "이메일 인증코드가 만료되었습니다"),
	WRONG_CODE(HttpStatus.BAD_REQUEST, "MEMBER4003", "이메일 인증코드가 올바르지 않습니다"),
	NOT_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "MEMBER4004", "존재하지 않는 메일입니다"),
	GO_TO_FINDPW(HttpStatus.BAD_REQUEST, "MEMBER4005", "비밀번호 찾기를 다시 하고 오세요"),
	NOT_SEND_EMAIL_CODE(HttpStatus.BAD_REQUEST, "MEMBER4006", "이메일 인증코드 전송에 실패했습니다"),
	PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "MEMBER4007", "비밀번호가 일치하지 않습니다"),
	NOT_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "MEMBER4041", "존재하지 않는 회원입니다.")
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
