package com.example.proiect_iot.sensorsservice;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class SensorService {

    private final String configFolder = "sensor-configs";

    public double getSensorValue(String id) {
        if (!SensorSimulator.exists(id)) {
            throw new IllegalArgumentException("Senzor negasit: " + id);
        }

        double raw = SensorSimulator.readValue(id);
        double scale = readScaleForSensor(id);
        return raw * scale;
    }

    public boolean createConfig(String id) throws IOException {
        File folder = new File(configFolder);
        if (!folder.exists()) folder.mkdirs();

        File configFile = new File(folder, id + "-config.txt");
        if (configFile.exists()) return false;

        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write("scale=1.0\n");
        }

        return true;
    }

    public boolean replaceConfig(String id, String filename, String newContent) throws IOException {
        File configFile = new File(configFolder, filename);
        if (!configFile.exists()) return false;

        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(newContent);
        }

        return true;
    }

    private double readScaleForSensor(String id) {
        File configFile = new File(configFolder, id + "-config.txt");
        if (!configFile.exists()) return 1.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line = reader.readLine();
            if (line != null && line.startsWith("scale=")) {
                return Double.parseDouble(line.substring(6));
            }
        } catch (Exception e) {
            return 1.0;
        }

        return 1.0;
    }
}
