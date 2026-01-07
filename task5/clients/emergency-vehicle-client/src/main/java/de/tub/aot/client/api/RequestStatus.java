package de.tub.aot.client.api;

public class RequestStatus {
    
    private String requestId;
    private String status;
    private String vehicleType;
    
    public RequestStatus() {
    }
    
    public RequestStatus(String requestId, String status, String vehicleType) {
        this.requestId = requestId;
        this.status = status;
        this.vehicleType = vehicleType;
    }
    
    public String getRequestId() {
        return requestId;
    }
    
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getVehicleType() {
        return vehicleType;
    }
    
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}

