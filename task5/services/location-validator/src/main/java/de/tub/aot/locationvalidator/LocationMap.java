package de.tub.aot.locationvalidator;

public class LocationMap {

    public LocationMap() {
    }

    public boolean validateLocation(String vehicleId, Coordinates coordinates) {
        // TODO: Implement actual location validation logic
        // For now, basic validation: coordinates must be within valid ranges
        if (coordinates == null) {
            return false;
        }
        
        // Validate latitude: -90 to 90
        if (coordinates.getLatitude() < -90.0 || coordinates.getLatitude() > 90.0) {
            return false;
        }
        
        // Validate longitude: -180 to 180
        if (coordinates.getLongitude() < -180.0 || coordinates.getLongitude() > 180.0) {
            return false;
        }
        
        return true;
    }
}

