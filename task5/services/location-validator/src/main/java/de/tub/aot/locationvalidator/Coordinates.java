package de.tub.aot.locationvalidator;

/**
 * Represents geographic coordinates with validation.
 * Latitude must be between -90 and 90 degrees.
 * Longitude must be between -180 and 180 degrees.
 */
public class Coordinates {
    
    private double latitude;
    private double longitude;
    
    public Coordinates() {
    }
    
    public Coordinates(double latitude, double longitude) {
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }
    
    public double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(double latitude) {
        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees");
        }
        this.latitude = latitude;
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees");
        }
        this.longitude = longitude;
    }
}

