package org.gomgom.parkingplace.Service.user;

import jakarta.transaction.Transactional;
import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;


    @Test
    @Transactional
    void modifyUserPassword() {
        User user = userRepository.findById(1L).get();
        UserDto.RequestModifyPasswordDto dto = new UserDto.RequestModifyPasswordDto("zxcv1104!", "zxcvb1104!");
        userService.modifyUserPassword(user, dto);
    }
}