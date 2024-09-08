package org.gomgom.parkingplace.Service.jwt;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.util.JWTUtil;
import org.springframework.stereotype.Service;

/*
작성자: 오지수
 */
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JWTUtil jwtUtil;

    @Override
    public String createAccessToken(User user) {
        return jwtUtil.createAccessToken(user);
    }

    @Override
    public String createRefreshToken(User user) {
        return jwtUtil.createToken(user, 60 * 60 * 24); //하루
    }

    @Override
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    @Override
    public Long getUserIdFromToken(String token) {
        return jwtUtil.getUserId(token);
    }
}
