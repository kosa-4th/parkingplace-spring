package org.gomgom.parkingplace.Service.payment;

import org.gomgom.parkingplace.Dto.PaymentCancelDto;
import org.gomgom.parkingplace.Dto.PaymentDto;
import org.gomgom.parkingplace.Entity.Payment;
import org.gomgom.parkingplace.Entity.PaymentCancel;

public interface PaymentService {
     PaymentCancel cancelPayment(Long reservationId, PaymentCancelDto.RequestPaymentCancelDto requestPaymentCancelDto);
     Payment completePayment(Long reservationId, PaymentDto.RequestPaymentDto requestPaymentDto);
     PaymentDto.ResponseReservationPaymentDto getReservationPaymentInfo(Long reservationId);
}
