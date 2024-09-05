package org.gomgom.parkingplace.Service.user;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.AuthDto;
import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Entity.RefreshToken;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Exception.CustomExceptions;
import org.gomgom.parkingplace.Repository.RefreshTokenRepository;
import org.gomgom.parkingplace.Repository.UserRepository;
import org.gomgom.parkingplace.Service.jwt.JwtService;
import org.gomgom.parkingplace.enums.Role;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder;

    /*
    작성자: 오지수
    회원가입
     */
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

    /*
    작성자: 오지수
    로그인
     */
    @Override
    public AuthDto.AuthResponseDto signIn(UserDto.requestSignInDto user) {
        User signInUser = authenticate(user);
        String accessToken = jwtService.createAccessToken(signInUser);
        String refreshToken = jwtService.createRefreshToken(signInUser);

        // refresh Token 저장
        saveRefreshToken(signInUser, refreshToken);

        // dto 생성 및 반환
        return new AuthDto.AuthResponseDto(accessToken, refreshToken);
    }

    @Override
    public AuthDto.AuthResponseDto refreshToken(String refreshToken) {
        if (jwtService.validateToken(refreshToken)) {
            String userId = jwtService.getUserIdFromToken(refreshToken);
            User user = userRepository.findById(Long.parseLong(userId))
                    .orElseThrow(() -> new CustomExceptions.UserNotFoundException("사용자 정보가 존재하지 않습니다."));

            String newAccessToken = jwtService.createAccessToken(user);
            return new AuthDto.AuthResponseDto(newAccessToken, refreshToken);
        } else {
            throw new CustomExceptions.ValidationException("유효한 토큰값이 아닙니다." );

        }
    }

    private User authenticate(UserDto.requestSignInDto user) {
        // 회원 존재 여부 확인
        User signInUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new CustomExceptions.UserNotFoundException("존재하지 않는 회원 아이디 입니다."));

        // Role 확인
        Role userRole = signInUser.getAuth();
        if (user.getMembershipType().equals("personal")) {
            if (userRole != Role.ROLE_USER && userRole != Role.ROLE_SYSTEM_MANAGER) {
                throw new AccessDeniedException("1등록된 회원 정보가 없습니다.");
            }
        } else if (user.getMembershipType().equals("business")) {
            if (userRole != Role.ROLE_PARKING_MANAGER) {
                throw new AccessDeniedException("2등록된 회원 정보가 없습니다.");
            }
        } else {
            throw new IllegalArgumentException("3회원 정보가 일치하지 않습니다.");
        }

        // 비밀번호 일치 여부 확인
        boolean result = encoder.matches(user.getPassword(), signInUser.getPassword());
        if(!result) {
            throw new CustomExceptions.ValidationException("등록된 회원 정보와 일치하지 않습니다.");
        }

        return signInUser;
    }

    // Refresh Token 저장
    private void saveRefreshToken(User user, String refreshToken) {
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .refreshToken(refreshToken)
                .user(user)
                .build();
        refreshTokenRepository.save(refreshTokenEntity);
    }

}
