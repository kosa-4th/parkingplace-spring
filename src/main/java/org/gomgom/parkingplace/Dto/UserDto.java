package org.gomgom.parkingplace.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

public class UserDto {

    /*
    작성자: 오지수
    회원가입 requestDto
     */

    /**
     * 작성자: 오지수
     * 2024.09.07 : 회원가입 메일 인증 요청시 사용 dto
     */
    @Data
    public static class smtpRequestDto {
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email
        private String email;
    }

    /**
     * 작성자: 오지수
     * 2024.09.07 : 회원가입 메일 인증시 사용 dto
     */
    @Getter
    public static class smtpCodeRequestDto {
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email
        private String email;
        @NotBlank(message = "인증번호를 입력해주세요.")
        private String code;
    }

    @AllArgsConstructor
    @Getter
    public static class smtpResponseDto {
        private String message;
    }

    /**
     * 작성자: 오지수
     * ? : 회원가입 dto
     */
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

        private String selectedCar;

        private String carNum;
    }

    /**
     * 작성자: 오지수
     * ? : 로그인 requestDto
     */
    @AllArgsConstructor
    @Data
    public static class requestSignInDto {

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email
        private String email;

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        private String password;

        private String membershipType;
    }

    /*
    작성자: 오지수
    회원가입 responseDto
     */
    @AllArgsConstructor
    @Getter
    public static class responseSignupDto {
        private String message;
    }

    /**
     * 이거 사용하나?
     */
//    @Getter
//    @AllArgsConstructor
//    public static class responseSignInDto {
//        private String name;
//        private String email;
//        private String token;
//        private String auth;
//    }
}
