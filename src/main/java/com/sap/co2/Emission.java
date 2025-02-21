package com.sap.co2;

import java.util.HashMap;
import java.util.Map;

public enum Emission {

    // Small Cars
    DIESEL_CAR_SMALL("diesel-car-small", 142.0),
    PETROL_CAR_SMALL("petrol-car-small", 154.0),
    PLUGIN_HYBRID_CAR_SMALL("plugin-hybrid-car-small", 73.0),
    ELECTRIC_CAR_SMALL("electric-car-small", 50.0),

    // Medium Cars
    DIESEL_CAR_MEDIUM("diesel-car-medium", 171.0),
    PETROL_CAR_MEDIUM("petrol-car-medium", 192.0),
    PLUGIN_HYBRID_CAR_MEDIUM("plugin-hybrid-car-medium", 110.0),
    ELECTRIC_CAR_MEDIUM("electric-car-medium", 58.0),

    // Large Cars
    DIESEL_CAR_LARGE("diesel-car-large", 209.0),
    PETROL_CAR_LARGE("petrol-car-large", 282.0),
    PLUGIN_HYBRID_CAR_LARGE("plugin-hybrid-car-large", 126.0),
    ELECTRIC_CAR_LARGE("electric-car-large", 73.0),

    // Public Transport
    BUS_DEFAULT("bus-default", 27.0),
    TRAIN_DEFAULT("train-default", 6.0);

    private final String key;
    private final double emission;

    // Constructor
    Emission(String key, double emission) {
        this.key = key;
        this.emission = emission;
    }

    public double getEmission() {
        return emission;
    }

    public String getKey() {
        return key;
    }

    // Get Emission by Key
    public static Double getEmissionByKey(String key) {
        for (Emission e : Emission.values()) {
            if (e.getKey().equalsIgnoreCase(key)) {
                return e.getEmission();
            }
        }
        return null; // Return null if key is not found
    }
}