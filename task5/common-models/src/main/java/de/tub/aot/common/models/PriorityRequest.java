package de.tub.aot.common.models;
import java.util.UUID;

public class PriorityRequest {
    
    private String vehicleType;
    private String vehicleId;
    private UUID trafficLightId;
    
    public PriorityRequest() {
    }
    
    public String getVehicleType() {
        return vehicleType;
    }
    
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleId() {
        return this.vehicleId;
    }
    
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public UUID getTrafficLightId() {
        return this.trafficLightId;
    }
    
    public void setTrafficLightId(UUID trafficLightId) {
        this.trafficLightId = trafficLightId;
    }
}

