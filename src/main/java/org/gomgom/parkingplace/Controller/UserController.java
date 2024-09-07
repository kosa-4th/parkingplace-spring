package org.gomgom.parkingplace.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.AuthDto;
import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Exception.CustomExceptions;
import org.gomgom.parkingplace.Service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/open/users")
public class UserController {
    private final UserService userService;

    /*
    작성자: 오지수
    회원가입
     */
    @PostMapping()
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto.requsetUserDto user) {
        try {
            User userEntity = User.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(userService.join(userEntity));
        } catch (CustomExceptions.ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /*
    작성자: 오지수
    로그인
     */
    @PostMapping("/authorize")
    public ResponseEntity<?> signInUser(@Valid @RequestBody UserDto.requestSignInDto user) {
        try {
            return ResponseEntity.ok(userService.signIn(user));
        } catch (CustomExceptions.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (CustomExceptions.ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    /*
    작성자: 오지수
    리프레시 토큰
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody AuthDto.RefreshTokenRequestDto request) {
        try {
            return ResponseEntity.ok(userService.refreshToken(request.getRefreshToken()));
        } catch (CustomExceptions.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (CustomExceptions.ValidationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
