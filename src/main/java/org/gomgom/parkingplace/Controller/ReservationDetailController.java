package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Service.reservation.ReservationServiceImpl;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * @author 김경민
 * @Date 2024.09.14
 * @Controller 예약상세 컨트롤ㄹ러
 * */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation/{reservationId}")
public class ReservationDetailController {
    private final ReservationServiceImpl reservationService;

    /**@Date 2024.09.14
     * 예약취소 */
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/cancel/protected")
    public ResponseEntity<?> reservationCancel(@PathVariable Long reservationId,
                                               @AuthenticationPrincipal CustomUserDetails customDetails){
        int result = reservationService.cancelReservation(reservationId);
        if (result != 0) {
            // 예약 취소 성공 시 200 OK 상태와 함께 메시지 반환
            return ResponseEntity.status(HttpStatus.OK).body("예약취소완료");
        }

// 예약 취소 실패 시 400 Bad Request 상태와 함께 메시지 반환
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("예약취소 실패");
    }
}
