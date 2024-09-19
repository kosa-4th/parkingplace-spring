package org.gomgom.parkingplace.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.gomgom.parkingplace.enums.Bool;

public class PaymentDto {

    @Data
    @AllArgsConstructor
    public static class RequestPaymentDto{
        private String impUid;
        private String merchantUid;
        private Double amount;
        private String buyerEmail;
        private String buyerName;
        private String buyerTel;
        private String receiptUrl;
        private String status;
        private String cardName;
        private String cardNumber;
        private String paidAt;
        private Bool reservationConfirmed;
        private Bool paymentConfirmed;
    }


    @Getter
    @AllArgsConstructor
    public static class ResponseReservationPaymentDto{
        private String reservationUuid;
        private String startTime;
        private String endTime;
        private Integer totalPrice;
        private Bool wash;
        private Bool maintenance;
        private String userName;
        private String lotName;
        private Bool reservationConfirmed;
        private String userEmail;
    }
}
