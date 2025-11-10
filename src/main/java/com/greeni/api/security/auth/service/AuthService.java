package com.greeni.api.security.auth.service;

import com.greeni.api.security.auth.dto.AuthRequestDTO;
import com.greeni.api.security.auth.dto.AuthResponseDTO;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
	AuthResponseDTO.LoginResult login(AuthRequestDTO.Login loginRequest, HttpServletResponse response);
}
