package com.iot.auth.controller;

import com.iot.auth.model.AuthRequest;
import com.iot.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @GetMapping("/jwtStore")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        return authService.validateToken(request);
    }

    @DeleteMapping("/jwtStore")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return authService.logout(request);
    }
}
