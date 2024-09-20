package org.gomgom.parkingplace.Service.payment;

import org.gomgom.parkingplace.Dto.PaymentCancelDto;
import org.gomgom.parkingplace.Dto.PaymentDto;
import org.gomgom.parkingplace.Entity.Payment;
import org.gomgom.parkingplace.Entity.PaymentCancel;

public interface PaymentService {
    public PaymentCancel cancelPayment(Long reservationId, PaymentCancelDto.RequestPaymentCancelDto requestPaymentCancelDto);
    public Payment completePayment(Long reservationId, PaymentDto.RequestPaymentDto requestPaymentDto);
    public PaymentDto.ResponseReservationPaymentDto getReservationPaymentInfo(Long reservationId);
}
