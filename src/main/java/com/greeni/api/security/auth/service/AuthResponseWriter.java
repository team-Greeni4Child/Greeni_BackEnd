package com.greeni.api.security.auth.service;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.apiPayload.status.ErrorReason;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthResponseWriter {

	private final ObjectMapper objectMapper;

	public void setErrorResponse(HttpServletResponse response,
		ErrorReason errorReason, String message) throws IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setStatus(errorReason.getHttpStatus().value());

		CommonResponse<String> res = CommonResponse
			.onFailure(errorReason, message);
		response.getWriter().write(objectMapper.writeValueAsString(res));
	}
}
