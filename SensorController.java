package com.example.proiect_iot.sensorsservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService service;

    public SensorController(SensorService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> readSensorValue(@PathVariable String id) {
        try {
            double value = service.getSensorValue(id);
            return ResponseEntity.ok("Senzor:" + value);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Senzor negasit: " + id);
        }
    }

    @PostMapping("/{id}/config")
    public ResponseEntity<String> createConfig(@PathVariable String id) throws IOException {
        if (!SensorSimulator.exists(id)) {
            return ResponseEntity.status(406).body("Senzor ID invalid: " + id);
        }

        if (service.createConfig(id)) {
            return ResponseEntity.ok("Senzor: " + id);
        } else {
            return ResponseEntity.status(409).body("Config deja existent pentru senzor:" + id);
        }
    }

    @PutMapping("/{id}/config/{filename}")
    public ResponseEntity<String> updateConfig(@PathVariable String id,
                                               @PathVariable String filename,
                                               @RequestBody String newContent) throws IOException {
        if (!SensorSimulator.exists(id)) {
            return ResponseEntity.status(406).body("Sensor ID invalid: " + id);
        }

        if (service.replaceConfig(id, filename, newContent)) {
            return ResponseEntity.ok("Config updated: " + filename);
        } else {
            return ResponseEntity.status(400).body("Config file not found: " + filename);
        }
    }
}
