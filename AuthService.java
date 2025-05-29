package com.iot.auth.service;


import com.iot.auth.model.AuthRequest;
import com.iot.auth.model.AuthResponse;
import com.iot.auth.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final JwtUtil jwtUtil;
    private final Map<String, String> users = new HashMap<>();
    private final Map<String, String> roles = new HashMap<>();

    public AuthService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        users.put("user1", "parola1"); roles.put("user1", "admin");
        users.put("user2", "parola2"); roles.put("user2", "owner");
        users.put("user3", "parolaX"); roles.put("user3", "owner");
    }

    public ResponseEntity<?> login(AuthRequest authRequest) {
        String user = authRequest.getUsername();
        if (users.containsKey(user) && users.get(user).equals(authRequest.getPassword())) {
            String token = jwtUtil.generateToken(user, roles.get(user));
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.status(401).body("Credentiale invalide :(");
    }

    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        String role = jwtUtil.extractRole(request);
        if (role == null) return ResponseEntity.status(404).body("Invalid token");
        return ResponseEntity.ok(role);
    }

    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = jwtUtil.extractToken(request);
        if (jwtUtil.invalidateToken(token)) return ResponseEntity.ok("Logged out");
        return ResponseEntity.status(404).body("Token-ul nu a fost gasit :(");
    }
}
