package com.sap.co2;


import com.sap.co2.service.DistanceService;
import com.sap.co2.service.GeocodeService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AppTest {

    /*@InjectMocks
    private App app;

    @Mock
    private DistanceService distanceService = new DistanceService();

    @Mock
    private GeocodeService geocodeService = new GeocodeService();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    public void testValidCalculation() throws Exception {
        when(geocodeService.getCoordinates("Berlin")).thenReturn("[13.4050,52.5200]");
        when(geocodeService.getCoordinates("Hamburg")).thenReturn("[9.9937,53.5511]");
        when(distanceService.getDistance("Berlin", "Hamburg")).thenReturn(289.0);

        Double emission = Emission.getEmissionByKey("diesel-car-medium");
        assertNotNull(emission);

        double totalCO2 = (289.0 * emission) / 1000;
        assertEquals(49.419, totalCO2, 0.001);
    }*/
}

