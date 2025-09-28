package com.greeni.api.members.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberRequestDTO {

    @Getter
    @NoArgsConstructor
    public static class SignUpDTO{

        @NotBlank(message = "이메일은 필수 입력입니다.")
        @Email
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(com|net|org|co\\.kr|go\\.kr)$",
                message = "유효한 이메일 도메인만 입력하세요."
        )
        private String email;

        @NotBlank(message = "비밀번호는 필수 입력입니다.")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$",
                message = "비밀번호는 8~15자이며, 영문자, 숫자, 특수문자를 모두 포함해야 합니다."
        )
        private String password;

        @NotBlank(message = "인증 코드는 필수 입력입니다.")
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
        private String email;
    }

}
