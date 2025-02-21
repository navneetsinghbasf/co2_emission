package com.sap.co2;

import com.sap.co2.service.DistanceService;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppTest {

    @Mock
    private DistanceService distanceService;

    @InjectMocks
    private App app = new App();

    // Capture console outputs
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private PrintStream originalOut;
    private PrintStream originalErr;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Redirect System.out & System.err
        originalOut = System.out;
        originalErr = System.err;
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void tearDown() {
        // Restore standard output streams
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    //Test successful execution of the program
    @Test
    void testMain_SuccessfulRun() throws Exception {

        String[] args = {
            "--start=Berlin",
            "--end=Hamburg",
            "--transportation-method=diesel-car-medium"
        };

        // Mock the distance so we skip real API calls
        when(distanceService.getDistance("Berlin", "Hamburg")).thenReturn(289.0);

        App.main(args);
        String output = outContent.toString();

        assertTrue(output.contains("49.3kg of CO2-equivalent"));
    }

    //Test one of the parameters missing
    @Test
    void testMain_MissingParameters() throws Exception {
        // Arrange: missing transportation-method
        String[] args = {
            "--start=Berlin",
            "--end=Hamburg"
        };

        App.main(args);
        String output = errContent.toString();

        assertTrue(output.contains("Missing required parameters: --start, --end, --transportation-method"));
    }

    //Testing incorrect vehicle Type
    @Test
    void testMain_InvalidVehicleType() throws Exception {
        // Arrange
        String[] args = {
            "--start=Berlin",
            "--end=Hamburg",
            "--transportation-method=unknown-vehicle"
        };

        when(distanceService.getDistance("Berlin", "Hamburg")).thenReturn(289.0);

        App.main(args);
        String error = errContent.toString();

        assertTrue(error.contains("Error: Invalid vehicle type."));
    }

    //Test Invalid City Name
    @Test
    void testMain_InvalidCityName() throws Exception {
        // Arrange
        String[] args = {
                "--start=abcdefg",
                "--end=Hamburg",
                "--transportation-method=diesel-car-medium"
        };

        App.main(args);
        String error = errContent.toString();

        assertTrue(error.contains("Invalid City Name"));
    }
}
