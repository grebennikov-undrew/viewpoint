package com.grebennikovas.viewpoint.security;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @GetMapping(path = "/basic_auth")
    @Operation(summary = "Basic аутентификация")
    public ResponseEntity<?> helloWorldBean() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
