package com.grebennikovas.viewpoint.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

@Component
public class JwtSessionAuthenticationStrategy implements SessionAuthenticationStrategy {

    // Добавляет токен в куки при успешной аутентификации
    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws SessionAuthenticationException {
        User user = (User) authentication.getPrincipal();
        Cookie jwtCookie = JwtUtils.generateCookie(user.getUsername());
        response.addCookie(jwtCookie);
    }
}
