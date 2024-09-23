package org.gomgom.parkingplace.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.gomgom.parkingplace.Entity.CarType;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Entity.PlateNumber;
import org.gomgom.parkingplace.enums.Role;

import java.util.List;


public class UserDto {

    /**
     * @Author 김경민
     * @Date 2024.09.23
     * 상속 받아서 주차쟁 관리자, 일반 회원 상세정보
     * */

    @Getter
    public static class ResponseUserDto extends ResponseAllUserDto {
        private List<PlateInfoDto> plateInfoList;  // 차량 번호와 차종 정보를 포함하는 리스트

        // 일반 사용자(User)용 생성자
        public ResponseUserDto(Long userId, Role auth, String email, String userName, List<PlateInfoDto> plateInfoList) {
            super(userId, auth, email, userName);
            this.plateInfoList = plateInfoList;
        }
    }

    // PlateNumber와 CarType 정보를 담는 DTO
    @Getter
    @AllArgsConstructor
    public static class PlateInfoDto {
        private String plateNumber;  // 차량 번호
        private String carTypeEnum;  // 차량 종류
    }

    @Getter
    @AllArgsConstructor
    public static class ParkingLotDto{
        private Long parkingLotId;
        private String name;
        private String address;
    }

    @Getter
    public static class ResponseParkingManagerDto extends ResponseAllUserDto {
        private List<ParkingLotDto> parkingLots;  // 주차장 리스트로 변경

        // 주차장 관리자(Parking Manager)용 생성자
        public ResponseParkingManagerDto(Long userId, Role auth, String email, String userName, List<ParkingLotDto> parkingLots) {
            super(userId, auth, email, userName);
            this.parkingLots = parkingLots;
        }
    }


    /**
     * @Author 김경민
     * @Date 2024.09.22
     * 일반 회원 정보 가져오기
     * */

    @Getter
    public static class ResponseAllUserDto {
        private Long userId;
        private Role auth;
        private String email;
        private String userName;

        public ResponseAllUserDto(Long userId, Role auth, String email, String userName) {
            this.userId = userId;
            this.auth = auth;
            this.email = email;
            this.userName = userName;
        }
    }



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
