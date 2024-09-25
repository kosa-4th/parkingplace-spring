package org.gomgom.parkingplace.Configure;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
/**
 * AppConfig 클래스는 Iamport API와 통신하기 위한 설정을 담당
 *
 * @author 김경민
 * @since 2024-09-03
 */
@Configuration
public class AppConfig {

    //IamPort API 사용을 위한 API 키와 Secret Key
    @Value("${iamport.apiKey}")
    String apiKey; //API 발급키
    //시크릿키
    @Value("${iamport.apiSecret}")
    String secretKey;

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, secretKey); //API 키와 시크릿키 사용하여 인스턴스 생성 및 반환
    }

}