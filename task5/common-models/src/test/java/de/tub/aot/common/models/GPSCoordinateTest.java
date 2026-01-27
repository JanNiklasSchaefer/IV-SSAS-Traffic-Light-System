package de.tub.aot.common.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GPSCoordinate class.
 * Tests DMS format handling, validation, and conversion to decimal degrees.
 */
class GPSCoordinateTest {

    @Test
    void testCreateValidLatitude() {
        GPSCoordinate coord = new GPSCoordinate(37, 45, 30.5, true);
        assertEquals(37, coord.getDegrees());
        assertEquals(45, coord.getMinutes());
        assertEquals(30.5, coord.getSeconds());
        assertTrue(coord.isLatitude());
    }

    @Test
    void testCreateValidLongitude() {
        GPSCoordinate coord = new GPSCoordinate(122, 30, 15.25, false);
        assertEquals(122, coord.getDegrees());
        assertEquals(30, coord.getMinutes());
        assertEquals(15.25, coord.getSeconds());
        assertFalse(coord.isLatitude());
    }

    @Test
    void testLatitudeOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GPSCoordinate(91, 0, 0.0, true);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new GPSCoordinate(-91, 0, 0.0, true);
        });
    }

    @Test
    void testLongitudeOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GPSCoordinate(181, 0, 0.0, false);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new GPSCoordinate(-181, 0, 0.0, false);
        });
    }

    @Test
    void testMinutesOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GPSCoordinate(37, 60, 0.0, true);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new GPSCoordinate(37, -1, 0.0, true);
        });
    }

    @Test
    void testSecondsOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GPSCoordinate(37, 0, 60.0, true);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new GPSCoordinate(37, 0, -0.1, true);
        });
    }

    @Test
    void testToDecimalDegrees() {
        // 37°45'30.5"N should be approximately 37.758472°
        GPSCoordinate coord = new GPSCoordinate(37, 45, 30.5, true);
        double decimal = coord.toDecimalDegrees();
        assertEquals(37.758472, decimal, 0.000001);
    }

    @Test
    void testToDecimalDegreesNegative() {
        // 122°30'15.25"W should be approximately -122.504236°
        GPSCoordinate coord = new GPSCoordinate(-122, 30, 15.25, false);
        double decimal = coord.toDecimalDegrees();
        assertEquals(-122.504236, decimal, 0.000001);
    }

    @Test
    void testFromDecimalDegrees() {
        double decimal = 37.758472;
        GPSCoordinate coord = GPSCoordinate.fromDecimalDegrees(decimal, true);

        assertEquals(37, coord.getDegrees());
        assertEquals(45, coord.getMinutes());
        assertEquals(30.5, coord.getSeconds(), 0.1);
        assertTrue(coord.isLatitude());
    }

    @Test
    void testFromDecimalDegreesNegative() {
        double decimal = -122.504236;
        GPSCoordinate coord = GPSCoordinate.fromDecimalDegrees(decimal, false);

        assertEquals(-122, coord.getDegrees());
        assertEquals(30, coord.getMinutes());
        assertEquals(15.25, coord.getSeconds(), 0.1);
        assertFalse(coord.isLatitude());
    }

    @Test
    void testToString() {
        GPSCoordinate coord = new GPSCoordinate(37, 45, 30.5, true);
        String str = coord.toString();
        assertTrue(str.contains("37°"), "Should contain degrees");
        assertTrue(str.contains("45'"), "Should contain minutes");
        assertTrue(str.contains("30") && (str.contains("30.5") || str.contains("30.500") || str.contains("30,5")),
                "Should contain seconds");
        assertTrue(str.contains("N"), "Should contain direction");
    }

    @Test
    void testToStringNegative() {
        GPSCoordinate coord = new GPSCoordinate(-122, 30, 15.25, false);
        String str = coord.toString();
        assertTrue(str.contains("122°"));
        assertTrue(str.contains("W"));
    }

    @Test
    void testEquals() {
        GPSCoordinate coord1 = new GPSCoordinate(37, 45, 30.5, true);
        GPSCoordinate coord2 = new GPSCoordinate(37, 45, 30.5, true);
        GPSCoordinate coord3 = new GPSCoordinate(37, 45, 30.6, true);

        assertEquals(coord1, coord2);
        assertNotEquals(coord1, coord3);
    }

    @Test
    void testHashCode() {
        GPSCoordinate coord1 = new GPSCoordinate(37, 45, 30.5, true);
        GPSCoordinate coord2 = new GPSCoordinate(37, 45, 30.5, true);

        assertEquals(coord1.hashCode(), coord2.hashCode());
    }

    @Test
    void testSetDegreesValidation() {
        GPSCoordinate coord = new GPSCoordinate(37, 0, 0.0, true);

        assertThrows(IllegalArgumentException.class, () -> {
            coord.setDegrees(91);
        });
    }

    @Test
    void testSetMinutesValidation() {
        GPSCoordinate coord = new GPSCoordinate(37, 0, 0.0, true);

        assertThrows(IllegalArgumentException.class, () -> {
            coord.setMinutes(60);
        });
    }

    @Test
    void testSetSecondsValidation() {
        GPSCoordinate coord = new GPSCoordinate(37, 0, 0.0, true);

        assertThrows(IllegalArgumentException.class, () -> {
            coord.setSeconds(60.0);
        });
    }
}
