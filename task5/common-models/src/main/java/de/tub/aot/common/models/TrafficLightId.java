package de.tub.aot.common.models;

import java.util.UUID;

/**
 * Represents a unique identifier for a traffic light.
 * Contains GPS position in DMS format and a UUID.
 * 
 * GPS coordinates must be within Duckburg city limits.
 * Duckburg is located away from 180°E/W to avoid coordinate system issues.
 */
public class TrafficLightId {

    private GPSCoordinate latitude;
    private GPSCoordinate longitude;
    private UUID uuid;
    private String direction;

    public TrafficLightId() {
    }

    /**
     * Creates a TrafficLightId with GPS coordinates and UUID.
     * 
     * @param latitude  GPS latitude coordinate in DMS format
     * @param longitude GPS longitude coordinate in DMS format
     * @param uuid      Unique identifier for the traffic light
     * @throws IllegalArgumentException if coordinates are not within Duckburg
     *                                  limits
     */
    public TrafficLightId(GPSCoordinate latitude, GPSCoordinate longitude, UUID uuid, String direction) {
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setUuid(uuid);
        this.setDirection(direction);
        validateDuckburgLocation();
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return this.direction;
    }

    public GPSCoordinate getLatitude() {
        return latitude;
    }

    public void setLatitude(GPSCoordinate latitude) {
        if (latitude == null) {
            throw new IllegalArgumentException("Latitude cannot be null");
        }
        if (!latitude.isLatitude()) {
            throw new IllegalArgumentException("Latitude coordinate must have isLatitude=true");
        }
        this.latitude = latitude;
    }

    public GPSCoordinate getLongitude() {
        return longitude;
    }

    public void setLongitude(GPSCoordinate longitude) {
        if (longitude == null) {
            throw new IllegalArgumentException("Longitude cannot be null");
        }
        if (longitude.isLatitude()) {
            throw new IllegalArgumentException("Longitude coordinate must have isLatitude=false");
        }
        this.longitude = longitude;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        this.uuid = uuid;
    }

    /**
     * Validates that the GPS coordinates are within Duckburg city limits.
     * Duckburg is assumed to be located in a reasonable location (not in Pacific
     * Ocean at 180°E/W).
     * 
     * For this implementation, we define Duckburg as:
     * - Latitude: approximately 37°N to 38°N (reasonable city latitude range)
     * - Longitude: approximately 120°W to 122°W (away from 180°E/W, reasonable city
     * longitude range)
     * 
     * These are example bounds - adjust based on actual Duckburg location
     * requirements.
     * 
     * @throws IllegalArgumentException if coordinates are outside Duckburg limits
     */
    private void validateDuckburgLocation() {
        double latDecimal = latitude.toDecimalDegrees();
        double lonDecimal = longitude.toDecimalDegrees();

        // Duckburg bounds (example - adjust as needed)
        // Avoiding 180°E/W as per requirements
        double duckburgLatMin = 37.0;
        double duckburgLatMax = 38.0;
        double duckburgLonMin = -122.0; // 122°W
        double duckburgLonMax = -120.0; // 120°W

        // Check if coordinates are within Duckburg
        if (latDecimal < duckburgLatMin || latDecimal > duckburgLatMax) {
            throw new IllegalArgumentException(
                    String.format("Latitude %.6f is outside Duckburg limits (%.1f to %.1f)",
                            latDecimal, duckburgLatMin, duckburgLatMax));
        }

        if (lonDecimal < duckburgLonMin || lonDecimal > duckburgLonMax) {
            throw new IllegalArgumentException(
                    String.format("Longitude %.6f is outside Duckburg limits (%.1f to %.1f)",
                            lonDecimal, duckburgLonMin, duckburgLonMax));
        }

        // Explicit check to avoid 180°E/W (Pacific Ocean issue)
        if (Math.abs(lonDecimal) >= 180.0) {
            throw new IllegalArgumentException(
                    "Longitude cannot be at 180°E/W (Pacific Ocean). Duckburg is not located there.");
        }
    }

    /**
     * Validates Duckburg location after coordinate changes.
     * Should be called after setting latitude or longitude.
     */
    public void validateLocation() {
        validateDuckburgLocation();
    }

    @Override
    public String toString() {
        return String.format("TrafficLightId[uuid=%s, location=%s, %s]",
                uuid, latitude, longitude);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        TrafficLightId that = (TrafficLightId) obj;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
