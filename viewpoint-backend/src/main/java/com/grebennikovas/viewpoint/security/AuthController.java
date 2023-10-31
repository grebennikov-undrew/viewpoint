package com.grebennikovas.viewpoint.security;

import com.grebennikovas.viewpoint.security.jwt.AuthRequest;
import com.grebennikovas.viewpoint.security.jwt.AuthResponse;
import com.grebennikovas.viewpoint.security.jwt.JwtTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenizer jwtTokenizer;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login (@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            ViewPointUserDetails userDetails = (ViewPointUserDetails) authentication.getPrincipal();
            String accessToken = jwtTokenizer.generateAccessToken(userDetails);
            AuthResponse response = new AuthResponse(userDetails.getUsername(),accessToken);
            return ResponseEntity.ok().body(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
