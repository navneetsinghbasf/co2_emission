package com.sap.co2;

import com.sap.co2.service.DistanceService;
import com.sap.co2.util.CommandLineParser;

import java.util.Map;
import java.util.Optional;

/**
 * Main Class that orchestrate the whole C02 Emission calculation
 */
public class App {

    public static void main(String[] args) throws Exception {

        try {
            Map<String, String> params = CommandLineParser.parseArgs(args);

            String start = params.get("start");
            String end = params.get("end");
            String transportationMethod = params.get("transportation-method");

            if (start == null || end == null || transportationMethod == null) {
                throw new IllegalArgumentException("Missing required parameters: --start, --end, --transportation-method");
            }

            DistanceService distanceService = new DistanceService();
            double distance = distanceService.getDistance(start, end);

            double co2PerKm = Optional.ofNullable(Emission.getEmissionByKey(transportationMethod))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle type."));

            double totalCO2 = (distance * co2PerKm) / 1000; // convert to kg
            System.out.printf("Your trip caused %.1fkg of CO2-equivalent.%n", totalCO2);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
        }
    }
}
