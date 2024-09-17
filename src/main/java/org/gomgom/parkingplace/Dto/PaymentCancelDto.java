package org.gomgom.parkingplace.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author 김경민
 * @Date 2024.09.13
 *
 * 결제 취소 관련 DTO
 * */
public class PaymentCancelDto {

    @Data
    @ToString
    public static class ResponsePaymentCancelDto{
        private String pgTid;
        private Double amount;
        private String receiptUrl; //취소영수증 url
        private String buyerName;
        private String buyerEmail;
        private String merchantUid;
    }

    @Data
    public static class RequestPaymentCancelDto{
        private String merchantUid;
        private String reason;
    }
}
