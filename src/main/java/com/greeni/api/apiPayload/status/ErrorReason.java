package com.greeni.api.apiPayload.status;

import org.springframework.http.HttpStatus;

public interface ErrorReason {

	HttpStatus getHttpStatus();

	String getCode();

	String getMessage();
}
