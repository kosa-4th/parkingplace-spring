package org.gomgom.parkingplace.Service.jwt;

import org.gomgom.parkingplace.Entity.User;
/*
작성자: 오지수
 */
public interface JwtService {

    String createAccessToken(User user);

    String createRefreshToken(User user);

    boolean validateToken(String token);

    String getUserIdFromToken(String token);

}
