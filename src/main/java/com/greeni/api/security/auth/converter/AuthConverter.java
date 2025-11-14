package com.greeni.api.security.auth.converter;

import com.greeni.api.security.auth.dto.AuthResponseDTO;

public class AuthConverter {

	public static AuthResponseDTO.LoginResult toLoginResult(String refreshToken, Long memberId) {
		return AuthResponseDTO.LoginResult.builder()
			.refreshToken(refreshToken)
			.memberId(memberId)
			.build();
	}
}
