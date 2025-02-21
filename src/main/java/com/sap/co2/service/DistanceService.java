package com.sap.co2.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.co2.util.AppConfig;
import com.sap.co2.util.AppUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.sap.co2.util.AppUtil.ORS_TOKEN;

public class DistanceService {

    public double getDistance(String start, String end) throws Exception {

        String apiKey = System.getenv(ORS_TOKEN);
        if (apiKey == null) {
            throw new IllegalStateException("ORS_TOKEN environment variable not set.");
        }

        GeocodeService geocodeService = new GeocodeService();
        String startCoords = geocodeService.getCoordinates(start);
        String endCoords = geocodeService.getCoordinates(end);

        URL url = new URL(AppConfig.get("distance.api.url"));
        String jsonPayload = String.format("{\"locations\":[%s,%s],\"metrics\":[\"distance\"]}", startCoords, endCoords);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", apiKey);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.getOutputStream().write(jsonPayload.getBytes());

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Get Distance failed: HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.toString());
        JsonNode distance = jsonNode.get("distances").get(0).get(1);

        if (distance == null) {
            throw new IOException("Could not calculate distance between " + start + " and " + end);
        }

        return distance.asDouble() / 1000;
    }
}
