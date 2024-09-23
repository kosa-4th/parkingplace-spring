package org.gomgom.parkingplace.Controller.SystemManager;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.UserDto.ResponseAllUserDto;
import org.gomgom.parkingplace.Service.user.UserService;
import org.gomgom.parkingplace.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 김경민
 * @Date 2024.09.22
 * 시스템 관리자 회원관리
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/System-Manager")
public class SystemManagerController {
    private final UserService userService;

    /**
     * @Author 김경민
     * @Date 2024.09.23
     * Modal 요청시 상세 데이터 가져오기
     * */
    @GetMapping("/userDetailData/protected")
    public ResponseEntity<?> getUserDetailData(@RequestParam Long userId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String auth = String.valueOf(customUserDetails.getUser().getAuth());

        if (!auth.equals("ROLE_SYSTEM_MANAGER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("확인할 수 없음.");
        }
        List<?> userDetailData = userService.getUserDetailData(userId);

        if(userDetailData == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("사용자 정보를 찾을 수 없습니다.");

        }
        return ResponseEntity.ok(userDetailData);
    }
    /**
     * @Author 김경민
     * @Date 2024.09.22
     * 유저 데이터 가져오기
     * */
    @GetMapping("/userData/protected")
    public ResponseEntity<?> getUserData(
            @RequestParam String requestAuth,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page, size);
        String auth = String.valueOf(customUserDetails.getUser().getAuth());

        if (!auth.equals("ROLE_SYSTEM_MANAGER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("확인할 수 없음.");
        }

        Page<ResponseAllUserDto> userData = userService.getAllUsers(Role.valueOf(requestAuth), pageable);
        // Check if data is empty
        if (userData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("데이터 없음");
        }

        return ResponseEntity.ok(userData);
    }
}