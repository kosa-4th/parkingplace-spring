package org.gomgom.parkingplace.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.AuthDto;
import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Exception.CustomExceptions;
import org.gomgom.parkingplace.Service.user.UserService;
import org.gomgom.parkingplace.enums.CarTypeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    /**
     * 작성자: 오지수
     * ? : 회원가입 페이지 로드시 차 종류 전달
     * @return 차량 종류
     */
    @GetMapping()
    public ResponseEntity<?> getUsers() {
        record CarTypeResponse(String carType) {}

        List<CarTypeResponse> response = CarTypeEnum.getFilteredCarTypes().stream()
                .map(CarTypeResponse::new)
                .toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 작성자: 오지수
     * ? : 회원 가입
     * @param userDto 회원가입 정보
     * @return /
     */
    @PostMapping()
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDto.requsetUserDto userDto) {
        userService.join(userDto);
        return ResponseEntity.noContent().build();
    }

    /**
     * 작성자: 오지수
     * ? : 로그인
     * @param user / 이메일, 비밀번호
     * @return
     */
    @PostMapping("/authorize")
    public ResponseEntity<AuthDto.AuthResponseDto> signInUser(@Valid @RequestBody UserDto.requestSignInDto user) {
        return ResponseEntity.ok(userService.signIn(user));
    }

    /**
     * 작성자: 오지수
     * ? : 리프레시 토큰
     * @param request
     * @return
     * 수정 필요
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthDto.AuthResponseDto> refresh(@RequestBody AuthDto.RefreshTokenRequestDto request) {
        return ResponseEntity.ok(userService.refreshToken(request.getRefreshToken()));
    }

}
