package com.sap.co2;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sap.co2.service.DistanceService;
import com.sap.co2.service.GeocodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DistanceServiceTest {

    @InjectMocks
    private DistanceService distanceService;

    @Mock
    private GeocodeService geocodeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDistanceSuccess() throws Exception {
        when(geocodeService.getCoordinates("Berlin")).thenReturn("[13.4050,52.5200]");
        when(geocodeService.getCoordinates("Hamburg")).thenReturn("[9.9937,53.5511]");

        double distance = distanceService.getDistance("Berlin", "Hamburg");
        assertTrue(distance > 0);
    }

    @Test
    void testGetDistanceInvalidAPIKey() {
        System.clearProperty("ORS_TOKEN");
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            distanceService.getDistance("Berlin", "Hamburg");
        });
        assertEquals("ORS_TOKEN not set in environment or application.properties", exception.getMessage());
    }

    @Test
    void testGetDistanceAPIFailure() throws Exception {
        when(geocodeService.getCoordinates("Berlin")).thenReturn("[13.4050,52.5200]");
        when(geocodeService.getCoordinates("Hamburg")).thenThrow(new RuntimeException("API Error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            distanceService.getDistance("Berlin", "Hamburg");
        });

        assertEquals("API Error", exception.getMessage());
    }
}
