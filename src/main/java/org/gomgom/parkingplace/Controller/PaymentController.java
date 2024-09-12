package org.gomgom.parkingplace.Controller;

import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Service.payment.IamportService;
import org.gomgom.parkingplace.Service.payment.PaymentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;

import static org.gomgom.parkingplace.Dto.PaymentDto.RequestPaymentDto;
import static org.gomgom.parkingplace.Dto.PaymentDto.ResponseReservationPaymentDto;

@RestController
@RequestMapping("/api/payment/{reservationId}")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentServiceImpl paymentService;
    private final IamportService iamportService;

    @GetMapping("/complete")
    public ResponseEntity<?> handlePaymentResult(
            @PathVariable("reservationId") Long reservationId,
            @RequestParam("imp_uid") String impUid,
            @RequestParam("merchant_uid") String merchantUid) throws IamportResponseException, IOException {

        // 액세스 토큰 발급
        String accessToken = iamportService.getAccessToken();
        System.out.println("accessToken" + accessToken);
        RequestPaymentDto requestPaymentDto = iamportService.verifyPayment(accessToken, impUid, merchantUid);
        if (requestPaymentDto == null) {
            // 실패 페이지로 리다이렉트
            String failedRedirectUrl = "http://localhost:5173/reservationDetail/" + reservationId + "?message=결제 실패!";
            return ResponseEntity.status(HttpStatus.FOUND)  // 302 리다이렉트 상태 코드
                    .location(URI.create(failedRedirectUrl))
                    .build();        }

        // 결제가 성공한 경우
        paymentService.completePayment(reservationId, requestPaymentDto);

        // 성공 메시지 반환
        String redirectUrl = "http://localhost:5173/reservationDetail/" + reservationId;
        return ResponseEntity.status(HttpStatus.FOUND)  // 302 리다이렉트 상태 코드
                .location(URI.create(redirectUrl))      // 리다이렉트할 URL 설정
                .build();

    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/protected")
//    @GetMapping("")
    public ResponseEntity<?> getReservationInfo(
            @PathVariable("reservationId") Long reservationId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {

            String userEmail = userDetails.getUser().getEmail();
            ResponseReservationPaymentDto reservationPaymentDto = paymentService.getReservationPaymentInfo(reservationId);


            return ResponseEntity.ok(reservationPaymentDto);
        } catch (IllegalArgumentException e) {

            // 정보가 없는 경우, 오류 메시지와 함께 403 FORBIDDEN 상태 반환
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 불가한 페이지입니다.");
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/complete/protected")
    public ResponseEntity<String> completePayment(
            @PathVariable("reservationId") Long reservationId,
            @RequestBody RequestPaymentDto requestPaymentDto) {


        // 결제 처리 서비스 호출
        paymentService.completePayment(reservationId, requestPaymentDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }


}
