package de.tub.aot.common.models;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TrafficLightId class.
 * Tests GPS coordinate validation, UUID handling, and Duckburg location
 * validation.
 */
class TrafficLightIdTest {

    private GPSCoordinate createDuckburgLatitude() {
        return new GPSCoordinate(37, 45, 30.5, true);
    }

    private GPSCoordinate createDuckburgLongitude() {
        return new GPSCoordinate(-121, 30, 15.25, false);
    }

    @Test
    void testCreateValidTrafficLightId() {
        GPSCoordinate lat = createDuckburgLatitude();
        GPSCoordinate lon = createDuckburgLongitude();
        UUID uuid = UUID.randomUUID();

        TrafficLightId id = new TrafficLightId(lat, lon, uuid, "north-south");

        assertEquals(lat, id.getLatitude());
        assertEquals(lon, id.getLongitude());
        assertEquals(uuid, id.getUuid());
    }

    @Test
    void testNullLatitude() {
        GPSCoordinate lon = createDuckburgLongitude();
        UUID uuid = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            new TrafficLightId(null, lon, uuid,"north-south");
        });
    }

    @Test
    void testNullLongitude() {
        GPSCoordinate lat = createDuckburgLatitude();
        UUID uuid = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            new TrafficLightId(lat, null, uuid,"north-south");
        });
    }

    @Test
    void testNullUUID() {
        GPSCoordinate lat = createDuckburgLatitude();
        GPSCoordinate lon = createDuckburgLongitude();

        assertThrows(IllegalArgumentException.class, () -> {
            new TrafficLightId(lat, lon, null,"north-south");
        });
    }

    @Test
    void testWrongLatitudeType() {
        GPSCoordinate lat = new GPSCoordinate(37, 45, 30.5, false); // Wrong: isLatitude=false
        GPSCoordinate lon = createDuckburgLongitude();
        UUID uuid = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            new TrafficLightId(lat, lon, uuid,"north-south");
        });
    }

    @Test
    void testWrongLongitudeType() {
        GPSCoordinate lat = createDuckburgLatitude();
        // Create a coordinate that would be valid as latitude but wrong type for
        // longitude
        // Use a value that's valid for latitude (e.g., 37) but marked as latitude when
        // it should be longitude
        GPSCoordinate lon = new GPSCoordinate(37, 30, 15.25, true); // Wrong: isLatitude=true but should be false
        UUID uuid = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            new TrafficLightId(lat, lon, uuid,"north-south");
        });
    }

    @Test
    void testDuckburgLocationValidation() {
        // Valid Duckburg location (within bounds)
        GPSCoordinate lat = new GPSCoordinate(37, 30, 0.0, true);
        GPSCoordinate lon = new GPSCoordinate(-121, 30, 0.0, false);
        UUID uuid = UUID.randomUUID();

        TrafficLightId id = new TrafficLightId(lat, lon, uuid,"north-south");
        assertNotNull(id);
    }

    @Test
    void testLocationOutsideDuckburgLatitude() {
        // Latitude too far north
        GPSCoordinate lat = new GPSCoordinate(39, 0, 0.0, true);
        GPSCoordinate lon = createDuckburgLongitude();
        UUID uuid = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            new TrafficLightId(lat, lon, uuid,"north-south");
        });
    }

    @Test
    void testLocationOutsideDuckburgLongitude() {
        // Longitude too far east
        GPSCoordinate lat = createDuckburgLatitude();
        GPSCoordinate lon = new GPSCoordinate(-119, 0, 0.0, false);
        UUID uuid = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            new TrafficLightId(lat, lon, uuid, "north-south");
        });
    }

    @Test
    void testLocationAt180Degrees() {
        // Test the explicit 180°E/W check (Pacific Ocean)
        GPSCoordinate lat = createDuckburgLatitude();
        GPSCoordinate lon = new GPSCoordinate(180, 0, 0.0, false);
        UUID uuid = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            new TrafficLightId(lat, lon, uuid,"north-south");
        });
    }

    @Test
    void testLocationAtMinus180Degrees() {
        // Test the explicit -180°E/W check (Pacific Ocean)
        GPSCoordinate lat = createDuckburgLatitude();
        GPSCoordinate lon = new GPSCoordinate(-180, 0, 0.0, false);
        UUID uuid = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            new TrafficLightId(lat, lon, uuid,"north-south");
        });
    }

    @Test
    void testSetLatitudeValidation() {
        GPSCoordinate lat = createDuckburgLatitude();
        GPSCoordinate lon = createDuckburgLongitude();
        UUID uuid = UUID.randomUUID();
        TrafficLightId id = new TrafficLightId(lat, lon, uuid,"north-south");

        // Try to set invalid latitude
        GPSCoordinate invalidLat = new GPSCoordinate(39, 0, 0.0, true);
        id.setLatitude(invalidLat);

        // Should throw when validating
        assertThrows(IllegalArgumentException.class, () -> {
            id.validateLocation();
        });
    }

    @Test
    void testSetLongitudeValidation() {
        GPSCoordinate lat = createDuckburgLatitude();
        GPSCoordinate lon = createDuckburgLongitude();
        UUID uuid = UUID.randomUUID();
        TrafficLightId id = new TrafficLightId(lat, lon, uuid,"north-south");

        // Try to set invalid longitude
        GPSCoordinate invalidLon = new GPSCoordinate(-119, 0, 0.0, false);
        id.setLongitude(invalidLon);

        // Should throw when validating
        assertThrows(IllegalArgumentException.class, () -> {
            id.validateLocation();
        });
    }

    @Test
    void testSetUuidNull() {
        GPSCoordinate lat = createDuckburgLatitude();
        GPSCoordinate lon = createDuckburgLongitude();
        UUID uuid = UUID.randomUUID();
        TrafficLightId id = new TrafficLightId(lat, lon, uuid,"north-south");

        assertThrows(IllegalArgumentException.class, () -> {
            id.setUuid(null);
        });
    }

    @Test
    void testToString() {
        GPSCoordinate lat = createDuckburgLatitude();
        GPSCoordinate lon = createDuckburgLongitude();
        UUID uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        TrafficLightId id = new TrafficLightId(lat, lon, uuid,"north-south");

        String str = id.toString();
        assertTrue(str.contains("TrafficLightId"));
        assertTrue(str.contains(uuid.toString()));
    }

    @Test
    void testEquals() {
        GPSCoordinate lat = createDuckburgLatitude();
        GPSCoordinate lon = createDuckburgLongitude();
        UUID uuid = UUID.randomUUID();

        TrafficLightId id1 = new TrafficLightId(lat, lon, uuid,"north-south");
        TrafficLightId id2 = new TrafficLightId(lat, lon, uuid,"north-south");
        TrafficLightId id3 = new TrafficLightId(lat, lon, UUID.randomUUID(),"north-south");

        assertEquals(id1, id2);
        assertNotEquals(id1, id3);
    }

    @Test
    void testHashCode() {
        GPSCoordinate lat = createDuckburgLatitude();
        GPSCoordinate lon = createDuckburgLongitude();
        UUID uuid = UUID.randomUUID();

        TrafficLightId id1 = new TrafficLightId(lat, lon, uuid,"north-south");
        TrafficLightId id2 = new TrafficLightId(lat, lon, uuid,"north-south");

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    void testDifferentUUIDsAreNotEqual() {
        GPSCoordinate lat = createDuckburgLatitude();
        GPSCoordinate lon = createDuckburgLongitude();

        TrafficLightId id1 = new TrafficLightId(lat, lon, UUID.randomUUID(),"north-south");
        TrafficLightId id2 = new TrafficLightId(lat, lon, UUID.randomUUID(),"north-south");

        assertNotEquals(id1, id2);
    }
}
