package com.grebennikovas.viewpoint.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:3000")
public class SecirutyController {
    @GetMapping(path = "/basic_auth")
    public ResponseEntity<?> helloWorldBean() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
