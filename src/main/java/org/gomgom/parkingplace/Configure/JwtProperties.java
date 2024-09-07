package org.gomgom.parkingplace.Configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
작성자: 오지수
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
