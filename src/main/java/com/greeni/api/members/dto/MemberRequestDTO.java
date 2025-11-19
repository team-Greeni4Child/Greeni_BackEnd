package com.greeni.api.members.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberRequestDTO {

	@Getter
	@NoArgsConstructor
	public static class SignUpDTO {

		@NotBlank(message = "이메일은 필수 입력입니다.")
		@Email
		@Pattern(
			regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(com|net|org|co\\.kr|go\\.kr)$",
			message = "유효한 이메일 도메인만 입력하세요."
		)
		@Schema(description = "사용자가 입력한 이메일", example = "greeni4child@gmail.com")
		private String email;

		@NotBlank(message = "비밀번호는 필수 입력입니다.")
		@Pattern(
				regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!-/:-@\\[-`\\{-~]).{8,}$",
				message = "비밀번호는 8자 이상이며, 영문자, 숫자, 특수문자를 모두 포함해야 합니다."
		)
		@Schema(description = "재설정할 비밀번호", example = "1234@@sdqq")
		private String password;

		@NotBlank(message = "인증 코드는 필수 입력입니다.")
		@Schema(description = "이메일로 전송된 인증번호", example = "123456")
		private String code;
	}

	@Getter
	@NoArgsConstructor
	public static class EmailDTO {

		@NotBlank(message = "이메일은 필수 입력입니다.")
		@Email
		@Pattern(
			regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(com|net|org|co\\.kr|go\\.kr)$",
			message = "유효한 이메일 도메인만 입력하세요."
		)
		@Schema(description = "사용자가 입력한 이메일", example = "greeni4child@gmail.com")
		private String email;
	}

	@Getter
	@NoArgsConstructor
	public static class PasswdDTO {

		@NotBlank(message = "이메일은 필수 입력입니다.")
		@Email
		@Pattern(
			regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(com|net|org|co\\.kr|go\\.kr)$",
			message = "유효한 이메일 도메인만 입력하세요."
		)
		@Schema(description = "사용자가 입력한 이메일", example = "greeni4child@gmail.com")
		private String email;

		@NotBlank(message = "인증 코드는 필수 입력입니다.")
		@Schema(description = "이메일로 전송된 인증번호", example = "123456")
		private String code;
	}

	@Getter
	@NoArgsConstructor
	public static class ResetPwDTO {

		@NotBlank(message = "이메일은 필수 입력입니다.")
		@Email
		@Pattern(
			regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(com|net|org|co\\.kr|go\\.kr)$",
			message = "유효한 이메일 도메인만 입력하세요."
		)
		@Schema(description = "사용자가 입력한 이메일", example = "greeni4child@gmail.com")
		private String email;

		@NotBlank(message = "비밀번호는 필수 입력입니다.")
		@Pattern(
				regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!-/:-@\\[-`\\{-~]).{8,}$",
				message = "비밀번호는 8자 이상이며, 영문자, 숫자, 특수문자를 모두 포함해야 합니다."
		)
		@Schema(description = "재설정할 비밀번호", example = "1234@@sdqq")
		private String password;
	}

	@Getter
	@NoArgsConstructor
	public static class ParentPasswordDTO {
		
		@NotBlank
		@Schema(description = "사용자가 입력한 부모 비밀번호", example = "1234asdf!")
		private String password;
	}
}
