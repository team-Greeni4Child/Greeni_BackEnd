package com.greeni.api.security.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.security.auth.docs.AuthControllerDocs;
import com.greeni.api.security.auth.dto.AuthRequestDTO;
import com.greeni.api.security.auth.dto.AuthResponseDTO;
import com.greeni.api.security.auth.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController implements AuthControllerDocs {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<CommonResponse<AuthResponseDTO.LoginResult>> login(
		@RequestBody @Valid AuthRequestDTO.Login loginRequest, HttpServletResponse response) {
		AuthResponseDTO.LoginResult loginResult = authService.login(loginRequest, response);
		return ResponseEntity.ok().body(CommonResponse.onSuccess(loginResult));
	}

	@PostMapping("/reissue")
	public ResponseEntity<CommonResponse<AuthResponseDTO.LoginResult>> reissue(
		@RequestHeader(name = "Refresh-Token") String refreshToken, HttpServletResponse response) {
		AuthResponseDTO.LoginResult result = authService.reissue(refreshToken, response);
		return ResponseEntity.ok().body(CommonResponse.onSuccess(result));
	}

	@PostMapping("/logout")
	public ResponseEntity<CommonResponse<Object>> logout(HttpServletRequest request) {
		authService.logout(request);
		return ResponseEntity.ok().body(CommonResponse.onSuccess(null));
	}
}
