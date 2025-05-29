package com.iot.auth.controller;

import com.iot.auth.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sensor")
public class SensorController {
    private final JwtUtil jwtUtil;

    public SensorController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/{sensorId}")
    public ResponseEntity<?> readSensor(@PathVariable String sensorId, HttpServletRequest request) {
        String role = jwtUtil.extractRole(request);
        if (role.equals("owner") || role.equals("admin")) {
            return ResponseEntity.ok("Senzor " + sensorId + " cu valoarea: " + Math.random() * 100);
        }
        return ResponseEntity.status(403).body("Acces interzis!!");
    }

    @PostMapping("/{sensorId}")
    public ResponseEntity<?> createConfig(@PathVariable String sensorId, HttpServletRequest request) {
        String role = jwtUtil.extractRole(request);
        if (!role.equals("admin")) return ResponseEntity.status(403).body("Acces interzis!!");
        return ResponseEntity.ok("Config pt senzorul " + sensorId + " creat");
    }

    @PutMapping("/{sensorId}/{configName}")
    public ResponseEntity<?> updateConfig(@PathVariable String sensorId, @PathVariable String configName, HttpServletRequest request) {
        String role = jwtUtil.extractRole(request);
        if (!role.equals("admin")) return ResponseEntity.status(403).body("Acces interzis!!");
        return ResponseEntity.ok("Config " + configName + " pt senzorul " + sensorId + " updatat");
    }
}
