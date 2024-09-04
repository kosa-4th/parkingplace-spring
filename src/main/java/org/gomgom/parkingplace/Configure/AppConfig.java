package org.gomgom.parkingplace.Configure;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig 클래스는 Iamport API와 통신하기 위한 설정을 담당
 *
 * @author 김경민
 * @since 2024-09-03
 */
@Configuration
public class AppConfig {

    //IamPort API 사용을 위한 API 키와 Secret Key
    String apiKey = "7711023827288188"; //API 발급키
    //시크릿키
    String secretKey = "ZvvwrTV74FB2yJbu8B4kWDgZJPfPDPyYdEf8xEUiYnyowUknHciXcu5p62a2WojIbLO73RspclXZLoxj";

    @Bean
    public IamportClient iamportClient(){
        return new IamportClient(apiKey, secretKey); //API 키와 시크릿키 사용하여 인스턴스 생성 및 반환
    }

}
