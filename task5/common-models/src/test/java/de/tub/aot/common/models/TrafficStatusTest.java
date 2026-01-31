package de.tub.aot.common.models;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TrafficStatus class.
 * Tests status values, timestamp handling, and pedestrian state logic.
 */
class TrafficStatusTest {

    private TrafficLightId createTestTrafficLight() {
        GPSCoordinate lat = new GPSCoordinate(37, 30, 0.0, true);
        GPSCoordinate lon = new GPSCoordinate(-121, 30, 0.0, false);
        UUID uuid = UUID.randomUUID();
        return new TrafficLightId(lat, lon, uuid, "north-south");
    }

    @Test
    void testCreateTrafficStatus() {
        TrafficLightId trafficLight = createTestTrafficLight();
        Instant timestamp = Instant.now();
        String state = "green";

        TrafficStatus status = new TrafficStatus(trafficLight, timestamp, state);

        assertEquals(trafficLight, status.getTrafficLightId());
        assertEquals(timestamp, status.getTimestamp());
        assertEquals(state, status.getState());
        assertEquals("red", status.getPedestrianState()); // green -> pedestrian red
    }

    @Test
    void testPedestrianStateLogic() {
        TrafficLightId trafficLight = createTestTrafficLight();
        Instant timestamp = Instant.now();

        // Test green state
        TrafficStatus greenStatus = new TrafficStatus(trafficLight, timestamp, "green");
        assertEquals("red", greenStatus.getPedestrianState());

        // Test yellow state
        TrafficStatus yellowStatus = new TrafficStatus(trafficLight, timestamp, "yellow");
        assertEquals("yellow", yellowStatus.getPedestrianState());

        // Test red state
        TrafficStatus redStatus = new TrafficStatus(trafficLight, timestamp, "red");
        assertEquals("green", redStatus.getPedestrianState());
    }

    @Test
    void testSetStateUpdatesPedestrianState() {
        TrafficLightId trafficLight = createTestTrafficLight();
        Instant timestamp = Instant.now();

        TrafficStatus status = new TrafficStatus(trafficLight, timestamp, "green");

        // Change to red
        status.setState("red");
        assertEquals("red", status.getState());
        assertEquals("green", status.getPedestrianState());

        // Change to yellow
        status.setState("yellow");
        assertEquals("yellow", status.getState());
        assertEquals("yellow", status.getPedestrianState());
    }

    @Test
    void testNullTrafficLightId() {
        Instant timestamp = Instant.now();

        assertThrows(IllegalArgumentException.class, () -> {
            new TrafficStatus(null, timestamp, "green");
        });
    }

    @Test
    void testNullTimestamp() {
        TrafficLightId trafficLight = createTestTrafficLight();

        TrafficStatus status = new TrafficStatus(trafficLight, null, "green");
        assertNotNull(status.getTimestamp()); // Should be set internally
    }

    @Test
    void testNullState() {
        TrafficLightId trafficLight = createTestTrafficLight();
        Instant timestamp = Instant.now();

        assertThrows(IllegalArgumentException.class, () -> {
            new TrafficStatus(trafficLight, timestamp, null);
        });
    }

    @Test
    void testSettersAndGetters() {
        TrafficLightId trafficLight = createTestTrafficLight();
        TrafficLightId newTrafficLight = createTestTrafficLight();
        Instant timestamp = Instant.now();
        Instant newTimestamp = Instant.now().plusSeconds(60);

        TrafficStatus status = new TrafficStatus(trafficLight, timestamp, "green");

        // Test setters
        status.setTrafficLightId(newTrafficLight);
        status.setTimestamp(newTimestamp);
        status.setState("red");

        // Test getters
        assertEquals(newTrafficLight, status.getTrafficLightId());
        assertEquals(newTimestamp, status.getTimestamp());
        assertEquals("red", status.getState());
        assertEquals("green", status.getPedestrianState()); // red -> pedestrian green
    }

    @Test
    void testDefaultPedestrianState() {
        TrafficLightId trafficLight = createTestTrafficLight();
        Instant timestamp = Instant.now();

        // Test unknown state
        TrafficStatus status = new TrafficStatus(trafficLight, timestamp, "unknown");
        assertEquals("unknown", status.getState());
        assertEquals("unknown", status.getPedestrianState()); // Unknown states get "unknown" pedestrian state
    }
}