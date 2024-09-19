package org.gomgom.parkingplace.Service.payment;

import org.gomgom.parkingplace.Dto.PaymentDto;
import org.gomgom.parkingplace.Entity.Payment;

public interface PaymentService {
    public Payment completePayment(Long reservationId, PaymentDto.RequestPaymentDto requestPaymentDto);
    public PaymentDto.ResponseReservationPaymentDto getReservationPaymentInfo(Long reservationId);
}
