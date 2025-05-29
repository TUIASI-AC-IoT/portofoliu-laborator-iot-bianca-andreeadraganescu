package com.example.proiect_iot.sensorsservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SensorSimulator {
    private static final Map<String, SensorType> sensors = new HashMap<>();
    private static final Random random = new Random();

    static {
        sensors.put("temp1", SensorType.TEMPERATURE);
        sensors.put("humidity1", SensorType.HUMIDITY);
        sensors.put("pressure1", SensorType.PRESSURE);
    }

    public static boolean exists(String id) {
        return sensors.containsKey(id);
    }

    public static double readValue(String id) {
        if (!sensors.containsKey(id)) return -1;

        switch (sensors.get(id)) {
            case TEMPERATURE: return 15 + random.nextDouble() * 10;
            case HUMIDITY: return 30 + random.nextDouble() * 50;
            case PRESSURE: return 950 + random.nextDouble() * 50;
            default: return -1;
        }
    }

    public enum SensorType {
        TEMPERATURE, HUMIDITY, PRESSURE
    }
}
