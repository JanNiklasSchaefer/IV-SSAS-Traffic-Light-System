package de.tub.aot.common.models;

public class PriorityResponse {
    
    private String status;
    private String requestId;
    private String statusMessage;
    
    public PriorityResponse() {
    }
    
    public PriorityResponse(String status, String requestId) {
        this.status = status;
        this.requestId = requestId;
    }

    public PriorityResponse(String status, String requestId, String statusMessage){
        this.status = status;
        this.requestId = requestId;
        this.statusMessage = statusMessage;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }
    
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}

