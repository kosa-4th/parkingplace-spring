package org.gomgom.parkingplace.Service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.AuthDto;
import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Dto.UserDto.ResponseAllUserDto;
import org.gomgom.parkingplace.Entity.*;
import org.gomgom.parkingplace.Exception.CustomExceptions;
import org.gomgom.parkingplace.Repository.*;
import org.gomgom.parkingplace.Service.jwt.JwtService;
import org.gomgom.parkingplace.enums.CarTypeEnum;
import org.gomgom.parkingplace.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder;
    private final PlateNumberRepository plateNumberRepository;
    private final CarTypeRepository carTypeRepository;
    private final ParkingLotRepository parkingLotRepository;

    @Override
    @Transactional
    public List<?> getUserDetailData(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null; // 사용자 없음
        }

        if (user.getAuth().equals(Role.ROLE_USER)) {
            // 일반 사용자일 경우 PlateNumber와 CarType 데이터를 가져옴
            List<UserDto.PlateInfoDto> plateInfoList = plateNumberRepository.findByUserId(userId).stream()
                    .map(plateNumber -> new UserDto.PlateInfoDto(
                            plateNumber.getPlateNumber(),
                            plateNumber.getCarType().getCarTypeEnum().name()
                    ))
                    .collect(Collectors.toList());

            // ResponseUserDto로 반환
            return List.of(new UserDto.ResponseUserDto(
                    user.getId(),
                    user.getAuth(),
                    user.getEmail(),
                    user.getName(),
                    plateInfoList
            ));

        } else if (user.getAuth().equals(Role.ROLE_PARKING_MANAGER)) {
            // ParkingLot 리스트를 ParkingLotDto 리스트로 변환
            List<UserDto.ParkingLotDto> parkingLotDtos = parkingLotRepository.findByUserId(userId).stream()
                    .map(parkingLot -> new UserDto.ParkingLotDto(
                            parkingLot.getId(),
                            parkingLot.getName(),
                            parkingLot.getAddress()
                    ))
                    .collect(Collectors.toList());
            // ResponseParkingManagerDto로 반환
            return List.of(new UserDto.ResponseParkingManagerDto(
                    user.getId(),
                    user.getAuth(),
                    user.getEmail(),
                    user.getName(),
                    parkingLotDtos
            ));
        }

        return Collections.emptyList(); // 해당하는 경우가 없을 때
    }

    /**
     * @Author 김경민
     * @Date 2024.09.22
     * getAllUsers
     * 기본 정보 가져오기
     */
    @Override
    @Transactional
    public Page<ResponseAllUserDto> getAllUsers(Role requestAuth, Pageable pageable) {
        Page<User> usersPage = userRepository.findAllByAuth(requestAuth, pageable);

        return usersPage.map(user -> new ResponseAllUserDto(
                user.getId(),
                user.getAuth(),
                user.getEmail(),
                user.getName()
        ));
    }


    /*
        작성자: 오지수
        회원가입
         */
    @Override
    @Transactional
    public void join(UserDto.requsetUserDto userDto) {
        userRepository.findByEmail(userDto.getEmail())
                .ifPresent(user -> {
                    throw new CustomExceptions.ValidationException("이미 가입된 사용자입니다.");
                });

        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(encoder.encode(userDto.getPassword()))
                .build();
        userRepository.save(user);

        CarType carType = carTypeRepository.findByCarTypeEnum(CarTypeEnum.fromKorName(userDto.getSelectedCar()));
        if (carType == null) {
            throw new CustomExceptions.ValidationException("잘못된 차량 정보입니다.");
        }
        PlateNumber plateNumber = PlateNumber.builder()
                .user(user)
                .carType(carType)
                .plateNumber(userDto.getCarNum())
                .build();
        plateNumberRepository.save(plateNumber);
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
        if (!jwtService.validateToken(refreshToken)) {
            throw new CustomExceptions.ValidationException("유효한 토큰값이 아닙니다.");
        }

        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomExceptions.UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        String newAccessToken = jwtService.createAccessToken(user);
        return new AuthDto.AuthResponseDto(newAccessToken, refreshToken);
    }

    @Override
    @Transactional
    public AuthDto.AuthResponseDto googleSignIn(String googleToken) {
        String url = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=" + googleToken;

            RestTemplate restTemplate = new RestTemplate();
            UserDto.GoogleUser googleUser = restTemplate.getForObject(url, UserDto.GoogleUser.class);
            //
            User user = userRepository.findByEmail(googleUser.getEmail())
                    .orElse(null);


            if (user == null) {
                // 회원가입
                User newUser = User.builder()
                        .name(googleUser.getName())
                        .email(googleUser.getEmail())
                        .googleId(googleUser.getId())
                        .build();
                userRepository.save(newUser);
                user = newUser;
            }

            // 비밀번호 일치 여부 확인
            if (user.getPassword() != null && user.getGoogleId() == null) {
                throw new CustomExceptions.ValidationException("이미 존재하는 사용자입니다.");
            }

            String accessToken = jwtService.createAccessToken(user);
            String refreshToken = jwtService.createRefreshToken(user);

            // refresh Token 저장
            saveRefreshToken(user, refreshToken);

            // dto 생성 및 반환
            return new AuthDto.AuthResponseDto(accessToken, refreshToken);

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
        if (!result) {
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
