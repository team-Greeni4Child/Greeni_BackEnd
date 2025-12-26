package com.greeni.api.security.jwt.filter;

import java.io.IOException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.greeni.api.apiPayload.handler.GeneralException;
import com.greeni.api.apiPayload.status.CommonErrorStatus;
import com.greeni.api.apiPayload.status.MemberErrorStatus;
import com.greeni.api.security.auth.service.AuthResponseWriter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

	private final AuthResponseWriter authResponseWriter;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			filterChain.doFilter(request, response);
		} catch (GeneralException e) {
			authResponseWriter.setErrorResponse(response, e.getErrorReason(), e.getMessage());
		} catch (UsernameNotFoundException e) {
			authResponseWriter.setErrorResponse(response, MemberErrorStatus.NOT_EXIST_EMAIL, e.getMessage());
		} catch (Exception e) {
			log.error("Unexpected error: {}", e.getMessage());
			authResponseWriter.setErrorResponse(response, CommonErrorStatus._INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
