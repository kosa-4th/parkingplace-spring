package org.gomgom.parkingplace.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserDto.responseSignupDto> createUser(@Valid @RequestBody UserDto.requsetUserDto user) {
        User userEntity = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(userService.join(userEntity));
    }
}
