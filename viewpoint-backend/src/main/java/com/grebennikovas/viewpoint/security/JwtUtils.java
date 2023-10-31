package com.grebennikovas.viewpoint.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

public class JwtUtils {

//    @Value("${security.jwt.secret}")
    private static final String SECRET_KEY = "abcdefghijklmnopqqrstuvwxyz";
    public static final String COOKIE_NAME = "token";
    private static final Algorithm JWT_ALGORITHM = Algorithm.HMAC256(SECRET_KEY);
    private static final JWTVerifier JWT_VERIFIER = JWT.require(JWT_ALGORITHM).build();
    private static final int MAX_AGE_SECONDS = 120;
    private static final int MAX_REFRESH_WINDOW_SECONDS = 30;

    static Cookie generateCookie(String username) {
        Instant now = Instant.now();
        String token = JWT.create()
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(MAX_AGE_SECONDS))
                .withClaim("username",username)
                .sign(JWT_ALGORITHM);
        Cookie jwtCookie = new Cookie(COOKIE_NAME, token);
        jwtCookie.setMaxAge(MAX_AGE_SECONDS);
        return jwtCookie;
    }

    static Optional<String> getToken(HttpServletRequest request) {
        // Получение всех куки
        Cookie[] cookies = request.getCookies();
        // Найти куки с токеном
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(COOKIE_NAME)) {
                    return Optional.of(cookie.getValue());
                }
            }
        }
        return Optional.empty();
    }

    static Optional<DecodedJWT> getValidatedToken(String token) {
        // Валидация токена
        try {
            return Optional.of(JWT_VERIFIER.verify(token));
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }

    // Проверяеет, входит ли токен в промежуток для обновления
    static boolean isRefreshable(HttpServletRequest request) {
        Optional<String> token = getToken(request);
        if (token.isEmpty()) return false;
        Instant expTime = JWT.decode(token.get()).getExpiresAtAsInstant();
        Instant canBeRefreshedAfter = expTime.minusSeconds(MAX_REFRESH_WINDOW_SECONDS);
        return Instant.now().isAfter(canBeRefreshedAfter);
    }

}
