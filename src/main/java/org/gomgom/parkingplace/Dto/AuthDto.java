package org.gomgom.parkingplace.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AuthDto {

    @Getter
    @AllArgsConstructor
    public static class AuthResponseDto {
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    public static class RefreshTokenRequestDto {
        private String refreshToken;
    }
}
