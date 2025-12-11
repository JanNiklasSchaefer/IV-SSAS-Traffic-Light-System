package de.tub.aot.locationvalidator;

public class LocationValidationRequest {
    
    private String vehicleId;
    private Coordinates coordinates;
    
    public LocationValidationRequest() {
    }
    
    public LocationValidationRequest(String vehicleId, Coordinates coordinates) {
        this.vehicleId = vehicleId;
        this.coordinates = coordinates;
    }
    
    public String getVehicleId() {
        return vehicleId;
    }
    
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public Coordinates getCoordinates() {
        return coordinates;
    }
    
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}

