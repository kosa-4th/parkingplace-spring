package org.gomgom.parkingplace.Service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Exception.CustomExceptions;
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
                throw new CustomExceptions.ValidationException("이미 가입된 사용자입니다.");
            }
            user.updatePassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            return new UserDto.responseSignupDto("success");
        } catch (CustomExceptions.ValidationException e) {
            throw e; //예외 다시 던지기
        } catch (Exception e) {
            throw new RuntimeException("알 수 없는 오류가 발생했습니다.", e);
        }
    }
}
