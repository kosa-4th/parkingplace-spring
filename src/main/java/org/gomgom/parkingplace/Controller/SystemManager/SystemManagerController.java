package org.gomgom.parkingplace.Controller.SystemManager;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.gomgom.parkingplace.Dto.UserDto.ResponseAllUserDto;
import org.gomgom.parkingplace.Service.parkingLot.ParkingLotService;
import org.gomgom.parkingplace.Service.user.UserService;
import org.gomgom.parkingplace.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    private final ParkingLotService parkingLotService;

    /**
     * @Author 김경민
     * @Date 2024.09.25
     */

    @PostMapping("/parkingLotData/create/protected")
    public ResponseEntity<?> createLotData(
            @RequestBody ParkingLotDto.RequestCreateLotDto requestCreateLotDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        String auth = customUserDetails.getUser().getAuth().toString();

        // 관리자 권한만 허용
        if (!auth.equals("ROLE_SYSTEM_MANAGER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("생성 권한이 없습니다.");
        }

        System.out.println("!!!!!!!!!!!!!!!!!!!" + requestCreateLotDto.getUserEmail());
        // 수정 작업 수행
        int result = parkingLotService.createLotData(requestCreateLotDto);

        if (result == 1) {
            return ResponseEntity.status(HttpStatus.OK).body("생성 완료");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("생성 실패");
        }
    }

    /**
     * @Author 김경민
     * @Date 2024.09.24
     * <p>
     * 주차장 수정 여부
     */
    @PutMapping("/parkingLotData/modify/protected")
    public ResponseEntity<?> modifyLotData(
            @RequestBody ParkingLotDto.RequestModifyLotDto requestModifyLotDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        String auth = customUserDetails.getUser().getAuth().toString();

        // 관리자 권한만 허용
        if (!auth.equals("ROLE_SYSTEM_MANAGER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정 권한이 없습니다.");
        }

        System.out.println("!!!!!!!!!!!!!!!!!!!" + requestModifyLotDto.getUserEmail());
        // 수정 작업 수행
        int result = parkingLotService.modifyLotData(requestModifyLotDto);

        if (result == 1) {
            return ResponseEntity.status(HttpStatus.OK).body("수정 완료");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정 실패");
        }
    }


    /***
     * @Author 김경민
     * @Date 2024.09.23
     *
     * 주차장 정보 조회
     */
    @GetMapping("/parkingLotData/protected")
    public ResponseEntity<Page<ParkingLotDto.ResponseParkingLotDto>> getParkingLotData(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        String auth = String.valueOf(customUserDetails.getUser().getAuth());

        if (!auth.equals("ROLE_SYSTEM_MANAGER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<ParkingLotDto.ResponseParkingLotDto> parkingLotDataPage = parkingLotService.getParkingLotData(name, address, pageable);

        if (parkingLotDataPage.isEmpty()) {
            return ResponseEntity.ok(parkingLotDataPage);
        }

        return ResponseEntity.ok(parkingLotDataPage);
    }

    /**
     * @Author 김경민
     * @Date 2024.09.23
     * Modal 요청시 상세 데이터 가져오기
     */
    @GetMapping("/userDetailData/protected")
    public ResponseEntity<?> getUserDetailData(@RequestParam Long userId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String auth = String.valueOf(customUserDetails.getUser().getAuth());

        if (!auth.equals("ROLE_SYSTEM_MANAGER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("확인할 수 없음.");
        }
        List<?> userDetailData = userService.getUserDetailData(userId);

        if (userDetailData == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("사용자 정보를 찾을 수 없습니다.");

        }
        return ResponseEntity.ok(userDetailData);
    }

    /**
     * @Author 김경민
     * @Date 2024.09.22
     * 유저 데이터 가져오기
     */
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