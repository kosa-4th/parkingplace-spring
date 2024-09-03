package org.gomgom.parkingplace.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

public class UserDto {

    @Data
    public static class requsetUserDto {
        private long userId;

        @NotBlank
        private String name;

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email
        private String email;

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
        private String password;
    }

    @AllArgsConstructor
    @Getter
    public static class responseSignupDto {
        private String message;
    }
}
