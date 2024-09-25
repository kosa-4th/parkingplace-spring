package org.gomgom.parkingplace.Service.payment;

import org.gomgom.parkingplace.Dto.PaymentDto;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static org.gomgom.parkingplace.Dto.PaymentCancelDto.ResponsePaymentCancelDto;

@Service
public class IamportService {

    private final WebClient webClient = WebClient.builder().baseUrl("https://api.iamport.kr").build();

    @Value("${iamport.apiKey}")
    private String apiKey;

    @Value("${iamport.apiSecret}")
    private String secretKey;


    /**
     * @Author 김경민
     * @Date 2024.09.13
     * 결제 취소 관련 메소드 생성
     */
    public ResponseEntity<ResponsePaymentCancelDto> cancelPayment(String merchantUid, String reason) {
        String accessToken = getAccessToken();

        WebClient.ResponseSpec response = webClient.post()
                .uri("/payments/cancel")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of(
                        "merchant_uid", merchantUid,   // 주문번호
                        "reason", reason               // 취소 사유
                ))
                .retrieve();

        // 응답을 Map 형식으로 받아오기
        Map<String, Object> paymentData = response.bodyToMono(Map.class).block();

        if (paymentData != null && paymentData.containsKey("response")) {
            // 'response' 키에 해당하는 값을 Map으로 캐스팅
            Map<String, Object> responseMap = (Map<String, Object>) paymentData.get("response");

            // 각 필드를 수동으로 추출해서 DTO로 매핑
            String pgTid = (String) responseMap.get("pg_tid");
            Double amount = responseMap.get("amount") != null ? ((Integer) responseMap.get("amount")).doubleValue() : null;
            String receiptUrl = (String) responseMap.get("receipt_url");
            String buyerName = (String) responseMap.get("buyer_name");
            String buyerEmail = (String) responseMap.get("buyer_email");
            String retrievedMerchantUid = (String) responseMap.get("merchant_uid");

            ResponsePaymentCancelDto responsePaymentCancelDto = new ResponsePaymentCancelDto();
            // ResponsePaymentCancelDto로 변환하여 반환
            responsePaymentCancelDto.setPgTid(pgTid);
            responsePaymentCancelDto.setAmount(amount);
            responsePaymentCancelDto.setReceiptUrl(receiptUrl);
            responsePaymentCancelDto.setBuyerName(buyerName);
            responsePaymentCancelDto.setBuyerEmail(buyerEmail);
            responsePaymentCancelDto.setMerchantUid(retrievedMerchantUid);

            // DTO 반환
            return ResponseEntity.ok(responsePaymentCancelDto);
        }

        // 오류가 발생했을 경우 null 반환
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
    }

    /**
     * @Author 김경민
     * @Date 2024.09.12
     * @Date 하드코딩 -> 시크릿키 보안
     * IamportAccessToken 발급
     * AceessToken 발급 후 문자열 처리
     */
    public String getAccessToken() {

        String tokenResponse = webClient.post()
                .uri("/users/getToken")
                .header("Content-Type", "application/json")
                .bodyValue(Map.of("imp_key", apiKey, "imp_secret", secretKey))
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
    public PaymentDto.RequestPaymentDto verifyPayment(String impUid, String merchantUid) {
        String accessToken = getAccessToken();

        WebClient.ResponseSpec response = webClient.get()
                .uri("/payments/" + impUid)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)  // 헤더 추가 부분 수정
                .retrieve();

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
                Bool paymentConfirmed = Bool.Y;

                return new PaymentDto.RequestPaymentDto(impUid, retrievedMerchantUid, amount, buyerEmail, buyerName, buyerTel, receiptUrl, status, cardName, cardNumber, paidAt, reservationConfirmed,paymentConfirmed);
            }

            return null;

        }
        return null;

    }


}
