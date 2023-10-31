package com.grebennikovas.viewpoint.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.grebennikovas.viewpoint.security.UserRepositoryDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Responsible for validating the JWT and extracting and populating user details
 */
@Component
public class JwtSecurityContextRepository implements SecurityContextRepository {

    private UserRepositoryDetailsService userDetailsService;

    // Use Spring dependency injection to assign the UserDetailsService instance
    // that we defined in the SecurityConfiguration class
    @Autowired
    public JwtSecurityContextRepository(UserRepositoryDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {

        HttpServletRequest request = requestResponseHolder.getRequest();
        Optional<String> token = JwtUtils.getToken(request);
        SecurityContext context = SecurityContextHolder.getContext();

        if (token.isEmpty()) {
            return context;
        }

        Optional<DecodedJWT> decodedJWT = JwtUtils.getValidatedToken(token.get());
        if (decodedJWT.isEmpty()) {
            return context;
        }

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(decodedJWT.get().getClaim("username").asString());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
        context.setAuthentication(usernamePasswordAuthenticationToken);
        return context;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request,
                            HttpServletResponse response) {}

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return JwtUtils.getToken(request).isPresent();
    }

}

