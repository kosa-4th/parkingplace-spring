package org.gomgom.parkingplace.Service.payment;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.PaymentCancelDto;
import org.gomgom.parkingplace.Entity.Payment;
import org.gomgom.parkingplace.Entity.PaymentCancel;
import org.gomgom.parkingplace.Entity.Reservation;
import org.gomgom.parkingplace.Repository.PaymentCancelRepository;
import org.gomgom.parkingplace.Repository.PaymentRepository;
import org.gomgom.parkingplace.Repository.ReservationRepository;
import org.gomgom.parkingplace.Service.reservation.ReservationServiceImpl;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

import static org.gomgom.parkingplace.Dto.PaymentCancelDto.ResponsePaymentCancelDto;
import static org.gomgom.parkingplace.Dto.PaymentDto.RequestPaymentDto;
import static org.gomgom.parkingplace.Dto.PaymentDto.ResponseReservationPaymentDto;

/**
 * @Author 김경민
 * @Date 2024.09.11 -> PC버전일 시 결제 내역 저장, 모바일 DB 접근 안되서, 다음 날까지
 * @Date 2024.09.12 -> 모바일 결제 완료
 */
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final IamportService iamportService;
    private final PaymentCancelRepository paymentCancelRepository;
    private final ReservationServiceImpl reservationService;

    @Transactional
    public PaymentCancel cancelPayment(Long reservationId, PaymentCancelDto.RequestPaymentCancelDto requestPaymentCancelDto) {
        ResponseEntity<ResponsePaymentCancelDto> response = iamportService.cancelPayment(requestPaymentCancelDto.getMerchantUid(), requestPaymentCancelDto.getReason());
        int result = reservationService.cancelReservation(reservationId);
        System.out.println("결제 취소 : result" + result);
        PaymentCancel paymentCancel = new PaymentCancel();
        Payment payment = new Payment();
        int i = paymentRepository.updatePaymentStatusByUuid(requestPaymentCancelDto.getMerchantUid(), Bool.N);
        if (response.getStatusCode().is2xxSuccessful()) {
            ResponsePaymentCancelDto cancelDto = response.getBody();
            // 취소된 금액 및 영수증 URL을 처리할 수 있음
            paymentCancel.setReceiptUrl(cancelDto.getReceiptUrl());
            paymentCancel.setBuyerEmail(cancelDto.getBuyerEmail());
            paymentCancel.setAmount(cancelDto.getAmount());
            paymentCancel.setMerchant_uid(cancelDto.getMerchantUid());
            paymentCancel.setPgTid(cancelDto.getPgTid());
            paymentCancel.setPayment(paymentRepository.findByMerchantUid(requestPaymentCancelDto.getMerchantUid()));

            return paymentCancelRepository.save(paymentCancel);
        }
        return null;
    }

    /**
     * @Author 김경민
     * @Date 2024.09.11
     * 트랜잭션 처리
     * PC에서 결제 시 결제 내역에 대해 DB 적용
     * 예약 상태 (N -> C로 변경)
     * 예약 저장
     */
    @Override
    @Transactional
    public Payment completePayment(Long reservationId, RequestPaymentDto requestPaymentDto) {
        Payment payment = new Payment();

        Reservation reservation =
                reservationRepository.findReservationById(reservationId).orElseThrow(()
                        -> new IllegalArgumentException("Reservation가 존재 안함"));

        String merChanUid = requestPaymentDto.getMerchantUid();
        String buyerName = requestPaymentDto.getBuyerName();
        String buyerTel = requestPaymentDto.getBuyerTel();
        String buyerEmail = requestPaymentDto.getBuyerEmail();
        String receiptUrl = requestPaymentDto.getReceiptUrl();
        Double amount = requestPaymentDto.getAmount();
        String cardName = requestPaymentDto.getCardName();
        String cardNumber = requestPaymentDto.getCardNumber();
        String status = requestPaymentDto.getStatus();
        String impUid = requestPaymentDto.getImpUid();
        String paidAt = requestPaymentDto.getPaidAt();

        payment.setImpUid(impUid);
        payment.setMerchantUid(merChanUid);
        payment.setReservation(reservation);
        payment.setAmount(amount);
        payment.setBuyerEmail(buyerEmail);
        payment.setBuyerTel(buyerTel);
        payment.setBuyerName(buyerName);
        payment.setCardName(cardName);
        payment.setReceiptUrl(receiptUrl);
        payment.setCardNumber(cardNumber);
        payment.setStatus(status);
        payment.setPaidAt(paidAt);
        payment.setPaymentConfirmed(Bool.Y);
        reservationRepository.updateReservationStatus(reservationId, Bool.C);

        return paymentRepository.save(payment);
    }

    /**
     * @Author 김경민
     * @Date 2024.09.11
     * <p>
     * 예약 후 결제 시 결제페이지 데이터 전송
     */
    @Override
    public ResponseReservationPaymentDto getReservationPaymentInfo(Long reservationId) {
        Reservation reservation = reservationRepository.findReservationById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 정보 확인불가"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String startTime = reservation.getStartTime().format(formatter);
        String endTime = reservation.getEndTime().format(formatter);

        return new ResponseReservationPaymentDto(
                reservation.getReservationUuid(),
                startTime,
                endTime,
                reservation.getTotalPrice(),
                reservation.getWash(),
                reservation.getMaintenance(),
                reservation.getUser().getName(),
                reservation.getLotName(),
                reservation.getReservationConfirmed(),
                reservation.getUser().getEmail()
        );
    }


}
