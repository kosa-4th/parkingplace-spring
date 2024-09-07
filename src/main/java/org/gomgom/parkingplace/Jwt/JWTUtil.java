package org.gomgom.parkingplace.Jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.gomgom.parkingplace.Entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
/*
작성자: 오지수
 */
@Slf4j
@Component
public class JWTUtil {
    private final Key SECRET_KEY;
    private final long ACCESS_TOKEN_EXPIRES_TIME;

    public JWTUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration_time}") long expirationTime
    ) {
        byte[] keyBytes = secretKey.getBytes();
        this.SECRET_KEY = Keys.hmacShaKeyFor(keyBytes);
        this.ACCESS_TOKEN_EXPIRES_TIME = expirationTime;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // AccessToken 생성
    public String createAccessToken(User user) {
        return createToken(user, ACCESS_TOKEN_EXPIRES_TIME);
    }

    //
    public String createToken(User user, long expirationTime) {
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        claims.put("role", user.getAuth().name());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenExpiresAt = now.plusSeconds(expirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenExpiresAt.toInstant()))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();

    }

    //token에서 id 추출
    public Long getUserId(String token) {
        return parseClaims(token).get("id", Long.class);
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    //Jwt 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty");
        }
        return false;
    }

}