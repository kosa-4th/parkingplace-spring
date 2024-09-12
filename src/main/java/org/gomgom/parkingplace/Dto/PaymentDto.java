package org.gomgom.parkingplace.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.gomgom.parkingplace.enums.Bool;

public class PaymentDto {

    @Data
    @AllArgsConstructor
    public static class RequestPaymentDto{
        private String reservationUuid;
        private String buyerName;
        private String buyerTel;
        private String buyerEmail;
        private String receiptUrl;
        private Double amount;
        private String cardName;
        private String cardNumber;
        private String status;
        private Bool reservationConfirmed;
    }

    //모바일용
    @Getter
    @AllArgsConstructor
    public class ResponseMobilePaymentDto {
        private String impUid;
        private String merchantUid;
        private Double amount;
        private String buyerEmail;
        private String buyerName;
        private String receiptUrl;
        private String cardName;
        private String cardNumber;
        private String status;
        private boolean success;      // 결제 성공 여부

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
