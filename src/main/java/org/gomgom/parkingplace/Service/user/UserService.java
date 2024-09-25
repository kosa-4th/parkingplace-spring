package org.gomgom.parkingplace.Service.user;

import org.gomgom.parkingplace.Dto.AuthDto;
import static org.gomgom.parkingplace.Dto.UserDto.*;

import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    /**
     * @Author 김경민
     * @Date 2024.09.23*/
    List<?> getUserDetailData(Long userId);

    /**
     * @Author 김경민
     * @Date 2024.09.22
     *
     * 유저 정보 및 주차장관리자 서비스단 생성
     * */
    Page<ResponseAllUserDto> getAllUsers(Role requestAuth, Pageable pageable);
    // 작성자: 오지수
    // 회원가입
    void join(UserDto.requsetUserDto userDto);

    // 로그인
    AuthDto.AuthResponseDto signIn(UserDto.requestSignInDto user);

    // 리프레시 토큰
    AuthDto.AuthResponseDto refreshToken(String refreshToken);

    // 구글 로그인
    AuthDto.AuthResponseDto googleSignIn(String googleToken);

    // 비밀번호 수정
    void modifyUserPassword(User user, UserDto.RequestModifyPasswordDto dto);

    // 회원탈퇴
    void deleteUser(User user);
}
