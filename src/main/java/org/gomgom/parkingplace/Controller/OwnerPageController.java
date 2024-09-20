package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.PaymentCancelDto;
import org.gomgom.parkingplace.Dto.ReservationDto;
import org.gomgom.parkingplace.Service.payment.PaymentService;
import org.gomgom.parkingplace.Service.reservation.ReservationService;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/parking-manager")
@RequiredArgsConstructor
public class OwnerPageController {

    private final ReservationService reservationService;
    private final PaymentService paymentService;

    @PostMapping("/reservation/cancel/{reservationId}/protected")
    public ResponseEntity<?> paymentCancel(@PathVariable Long reservationId, @RequestBody PaymentCancelDto.RequestPaymentCancelDto requestPaymentCancelDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            System.out.println("여기는 뜨니?");
            String result = String.valueOf(paymentService.cancelPayment(reservationId, requestPaymentCancelDto));
            System.out.println("result"+result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("결제 취소 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * @Date 2024.09.20
     * 예약 상태 수정
     * */
    @PutMapping("/reservation/{reservationId}")
    public ResponseEntity<?> updateReservationStatus(
            @PathVariable Long reservationId,
            @RequestBody Map<String, String> body) {
        try {
            // 요청으로 받은 새로운 상태값
            Bool newStatus =Bool.valueOf(body.get("reservationConfirmed"));

            // 서비스 메소드 호출하여 예약 상태 업데이트
            int updated = reservationService.updateReservationStatus(reservationId, newStatus);

            // 상태 업데이트가 성공한 경우
            if (updated == 1) {
                return ResponseEntity.ok().body("요청 성공");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("예약실패.");
            }
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }
    /**
     *@Author 김경민
     *@Date 2024.09.19 -> Owner페이지 예약관련 정보*/
    @GetMapping("/reservation")
    public ResponseEntity<Page<ReservationDto.ResponseOwnerReservationDto>> getOwnerReservationData(
            @ModelAttribute ReservationDto.RequestOwnerReservationDto requestOwnerReservationDto, @RequestParam int page,@RequestParam int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<ReservationDto.ResponseOwnerReservationDto> reservationsPage = reservationService.getOwnerReservations(requestOwnerReservationDto, pageable);

        // 결과가 있을 경우
        if (reservationsPage != null && !reservationsPage.isEmpty()) {
            // 데이터를 담아서 200 OK 상태로 반환
            return ResponseEntity.ok(reservationsPage);
        } else {
            // 데이터가 없을 경우 204 No Content 반환
            return ResponseEntity.noContent().build();
        }
    }
}
