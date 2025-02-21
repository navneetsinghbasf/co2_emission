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
import java.util.Objects;
import java.util.Optional;

import static com.sap.co2.util.AppUtil.ORS_TOKEN;

public class GeocodeService {

    public String getCoordinates(String city) throws Exception {

        String apiKey = System.getenv(ORS_TOKEN);
        if (apiKey == null) {
            throw new IllegalStateException("ORS_TOKEN environment variable not set.");
        }
        String urlString = String.format(AppConfig.get("coordinates.service.url"), apiKey, city);
        JsonNode jsonNode = getNode(urlString);

        //JsonNode coordinates = jsonNode.get("features").get(0).get("geometry").get("coordinates");
        JsonNode coordinates = Optional.ofNullable(jsonNode.get("features"))
                .filter(JsonNode::isArray)
                .filter(features -> features.size() > 0)
                .map(features -> features.get(0).get("geometry"))
                .filter(Objects::nonNull)
                .map(geometry -> geometry.get("coordinates"))
                .filter(Objects::nonNull)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid City Name."
                ));

        if (coordinates == null) {
            throw new IOException("No coordinates found for city: " + city);
        }

        return coordinates.toString();
    }

    private JsonNode getNode(String urlString) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Get Distance failed: HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.toString());
        return jsonNode;
    }
}
