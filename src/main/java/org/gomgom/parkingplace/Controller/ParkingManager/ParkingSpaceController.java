package org.gomgom.parkingplace.Controller.ParkingManager;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.gomgom.parkingplace.Dto.ParkingSpaceDto;
import org.gomgom.parkingplace.Entity.ParkingSpace;
import org.gomgom.parkingplace.Service.parkingSpace.ParkingSpaceServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/***
 * @Date 2024.09.26
 * Contrller폴더 이동 및 @어노테이션 재설정
 * URL 재설정
 */

@RestController
@RequestMapping("/api/parking-manager")
@RequiredArgsConstructor
public class ParkingSpaceController {

    private final ParkingSpaceServiceImpl parkingSpaceService;

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.20
     * 설명 : 주차 구역 추가
     *  ---------------------
     * 2024.09.20 양건모 | 기능 구현
     * 2024.09.25 김경민 | 주소 수정 및 DTO 수정
     * */
    @PostMapping("/info/parkingArea/protected")
    @PreAuthorize("hasRole('ROLE_PARKING_MANAGER')")
    public ResponseEntity<?> insertParkingSpace(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ParkingSpaceDto.InsertParkingSpaceRequestDto request
            ) throws BadRequestException {
        System.out.println("=========================");
        System.out.println(request.getSpaceName());
        parkingSpaceService.insertParkingSpace(userDetails.getUser().getId(), request);
        return ResponseEntity.ok().build();
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.21
     * 설명 : 주차 구역 수정
     *  ---------------------
     * 2024.09.21 양건모 | 기능 구현
     * 2024.09.26 김경민 | 주소 수정
     * */
    @PutMapping("/info/parkingArea/protected")
    @PreAuthorize("hasRole('ROLE_PARKING_MANAGER')")
    public ResponseEntity<?> modifyParkingSpace(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ParkingSpaceDto.ModifyParkingSpaceRequestDto request
    ) throws BadRequestException {

        parkingSpaceService.modifyParkingSpace(userDetails.getUser().getId(), request);
        return ResponseEntity.ok().build();
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.22
     * 설명 : 주차 구역 삭제
     *  ---------------------
     * 2024.09.22 양건모 | 기능 구현
     * */
    @DeleteMapping("/info/parkingArea/protected")
    @PreAuthorize("hasRole('ROLE_PARKING_MANAGER')")
    public ResponseEntity<?> deleteParkingSpace(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestHeader("parkingSpaceId") long parkingSpaceId
    ) throws BadRequestException {
        parkingSpaceService.deleteParkingSpace(userDetails.getUser().getId(), parkingSpaceId);
        return ResponseEntity.ok().build();
    }
}
