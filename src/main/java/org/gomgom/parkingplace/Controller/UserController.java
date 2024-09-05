package org.gomgom.parkingplace.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.AuthDto;
import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    /*
    작성자: 오지수
    회원가입
     */
    @PostMapping()
    public ResponseEntity<UserDto.responseSignupDto> createUser(@Valid @RequestBody UserDto.requsetUserDto user) {
        User userEntity = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(userService.join(userEntity));
    }

    /*
    작성자: 오지수
    로그인
     */
    @PostMapping("/authorize")
    public ResponseEntity<AuthDto.AuthResponseDto> signInUser(@Valid @RequestBody UserDto.requestSignInDto user) {
        return ResponseEntity.ok(userService.signIn(user));

    }

    /*
    작성자: 오지수
    리프레시 토큰
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthDto.AuthResponseDto> refresh(@RequestBody AuthDto.RefreshTokenRequestDto request) {
        return ResponseEntity.ok(userService.refreshToken(request.getRefreshToken()));
    }

}
