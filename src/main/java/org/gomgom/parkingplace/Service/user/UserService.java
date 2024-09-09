package org.gomgom.parkingplace.Service.user;

import org.gomgom.parkingplace.Dto.AuthDto;
import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Entity.User;

public interface UserService {
    // 작성자: 오지수
    // 회원가입
    void join(UserDto.requsetUserDto userDto);

    // 로그인
    AuthDto.AuthResponseDto signIn(UserDto.requestSignInDto user);

    // 리프레시 토큰
    AuthDto.AuthResponseDto refreshToken(String refreshToken);
}
