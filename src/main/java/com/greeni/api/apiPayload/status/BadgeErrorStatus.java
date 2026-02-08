package com.greeni.api.apiPayload.status;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BadgeErrorStatus implements ErrorReason {
	BADGE_NOT_FOUND(HttpStatus.NOT_FOUND, "BADGE4041", "해당 배지를 찾을 수 없습니다."),
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
