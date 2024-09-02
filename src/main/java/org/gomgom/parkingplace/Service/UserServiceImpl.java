package org.gomgom.parkingplace.Service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public UserDto.responseSignupDto join(User user) {
        try {
            Optional<User> vailUser = userRepository.findByEmail(user.getEmail());
            if(vailUser.isPresent()) {
                throw new ValidationException("이미 가입된 사용자입니다.");
            }
            user.updatePassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            return new UserDto.responseSignupDto("success");
        } catch (Exception e) {
            throw new RuntimeException("알 수 없는 오류가 발생했습니다.", e);
        }
    }
}
