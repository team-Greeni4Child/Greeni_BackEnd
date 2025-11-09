package com.greeni.api.security.jwt.filter;

import java.io.IOException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.apiPayload.exception.GeneralException;
import com.greeni.api.apiPayload.status.CommonErrorStatus;
import com.greeni.api.apiPayload.status.MemberErrorStatus;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			filterChain.doFilter(request, response);
		} catch (GeneralException e) {
			CommonResponse.setErrorResponse(response, e.getErrorReason(), e.getMessage());
		} catch (UsernameNotFoundException e) {
			CommonResponse.setErrorResponse(response, MemberErrorStatus.NOT_EXIST_EMAIL, e.getMessage());
		} catch (Exception e) {
			log.error("Unexpected error: {}", e.getMessage());
			CommonResponse.setErrorResponse(response, CommonErrorStatus._INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
