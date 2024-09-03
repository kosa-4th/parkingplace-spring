package org.gomgom.parkingplace.Service;

import io.jsonwebtoken.*;
import org.gomgom.parkingplace.Entity.User;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
/*
작성자: 오지수
 */
@Service
public class JwtServiceImpl implements JwtService {
    private final String secretKey = "parple-secret-key-dkafjlekgjiej##Gdgklfaj25395!59786";
    private final String refreshSecretKey = "parple-refresh-secret-key-djkgh39@flkg!#5u29tgdklg#238ilk";

    @Override
    public String createAccessToken(User user) {
        return createToken(user, secretKey, 1000 * 60 * 15); //15분 유효
    }

    @Override
    public String createRefreshToken(User user) {
        return createToken(user, refreshSecretKey, 1000 * 60 * 60 * 24);
    }

    private String createToken(User user, String key, Integer duration) {
        Date expTime = new Date(System.currentTimeMillis() + duration);
        Key signKey = new SecretKeySpec(key.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("username", user.getName())
                .claim("email", user.getEmail())
                .claim("auth", user.getAuth().name())
                .setExpiration(expTime)
                .signWith(signKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(refreshSecretKey.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(refreshSecretKey.getBytes())
                .build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
