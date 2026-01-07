package de.tub.aot.client.api;

public class PriorityRequest {
    
    private String vehicleType;
    
    public PriorityRequest() {
    }
    
    public PriorityRequest(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    
    public String getVehicleType() {
        return vehicleType;
    }
    
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}

