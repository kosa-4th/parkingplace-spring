package org.gomgom.parkingplace.Service.payment;

import org.gomgom.parkingplace.Dto.PaymentDto;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class IamportService {

    private final WebClient webClient = WebClient.builder().baseUrl("https://api.iamport.kr").build();
//
//    /**
//     * @Author 김경민
//     * @Date 2024.09.13
//     * 결제 취소 관련 메소드 생성
//     */
//    public ResponseEntity<String> cancelPayment(String merchantUid, String reason) {
//        String accessToken = getAccessToken();
//
//        ResponseEntity<String> response = webClient.post()
//                .uri("/payments/cancel")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(Map.of(
//                        "merchant_uid", merchantUid,   // 주문번호
//                        "reason", reason               // 취소 사유
//                ))
//                .retrieve()
//                .toEntity(String.class)
//                .block();  // 동기식으로 실행
//
//        return response;  // 응답 반환
//    }

    /**
     * @Author 김경민
     * @Date 2024.09.12
     * IamportAccessToken 발급
     * AceessToken 발급 후 문자열 처리
     */
    public String getAccessToken() {

        String tokenResponse = webClient.post()
                .uri("/users/getToken")
                .bodyValue(Map.of("imp_key", "7711023827288188", "imp_secret", "YDZc3JidwbJRBgjv0Nrr1rfdXQ32ijp81NI7KWuwmnSI4s5TUC54TfF5iJaRQo6Jqg3AXGe5wv8qSzTa"))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String accessToken = null;

        if (tokenResponse != null && tokenResponse.contains("\"access_token\":\"")) {
            // access_token 부분만 잘라서 추출
            int startIndex = tokenResponse.indexOf("\"access_token\":\"") + 16; // "access_token": 앞부분을 건너뜀
            int endIndex = tokenResponse.indexOf("\"", startIndex); // "access_token" 값 끝에 있는 따옴표 찾기
            accessToken = tokenResponse.substring(startIndex, endIndex);
        }
        return accessToken;
    }

    /**
     * @Author 김경민
     * @Date 2024.09.12
     * 유효한 거래 내역인지 확인 후 데이터 저장
     */
    public PaymentDto.RequestPaymentDto verifyPayment(String accessToken, String impUid, String merchantUid) {
        WebClient.ResponseSpec response = webClient.get()
                .uri("/payments/" + impUid)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)  // 헤더 추가 부분 수정
                .retrieve();

        System.out.println("verifyPayment" + response);

        Map<String, Object> paymentData = response.bodyToMono(Map.class).block();
        if (paymentData != null && paymentData.containsKey("response")) {

            Map<String, Object> responseMap = (Map<String, Object>) paymentData.get("response");


            String retrievedMerchantUid = (String) responseMap.get("merchant_uid");

            // merchantUid가 일치하는지 확인
            if (retrievedMerchantUid != null && retrievedMerchantUid.equals(merchantUid)) {
                String buyerName = (String) responseMap.get("buyer_name");
                String buyerTel = (String) responseMap.get("buyer_tel");
                String buyerEmail = (String) responseMap.get("buyer_email");
                String receiptUrl = (String) responseMap.get("receipt_url");
                Double amount = responseMap.get("amount") != null ? ((Integer) responseMap.get("amount")).doubleValue() : null;
                String cardName = (String) responseMap.get("card_name");
                String cardNumber = (String) responseMap.get("card_number");
                String status = (String) responseMap.get("card_status");
                String paidAt = "";
                Bool reservationConfirmed = Bool.N;


                return new PaymentDto.RequestPaymentDto(impUid, retrievedMerchantUid, amount, buyerEmail, buyerName, buyerTel, receiptUrl, status, cardName, cardNumber, paidAt, reservationConfirmed);
            }

            return null;

        }
        return null;

    }


}
