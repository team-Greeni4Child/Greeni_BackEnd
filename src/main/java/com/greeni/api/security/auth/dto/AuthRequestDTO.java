package com.greeni.api.security.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthRequestDTO {

	@Getter
	@NoArgsConstructor
	public static class Login {

		@NotBlank
		@Schema(description = "사용자가 입력한 이메일", example = "greeni4child@gmail.com")
		@Email
		private String email;
		@NotBlank
		@Schema(description = "사용자가 입력한 비밀번호", example = "1234asdf!")
		private String password;
	}
}
