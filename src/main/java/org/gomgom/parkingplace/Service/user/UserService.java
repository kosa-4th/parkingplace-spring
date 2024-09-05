package org.gomgom.parkingplace.Service.user;

import org.gomgom.parkingplace.Dto.AuthDto;
import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Entity.User;

public interface UserService {
    // 작성자: 오지수
    // 회원가입
    public abstract UserDto.responseSignupDto join(User user);

    // 로그인
    public abstract AuthDto.AuthResponseDto signIn(UserDto.requestSignInDto user);

    // 리프레시 토큰
    public abstract AuthDto.AuthResponseDto refreshToken(String refreshToken);
}
