package de.tub.aot.tcc.priority;

public class PriorityResponse {
    
    private String status;
    private String requestId;
    
    public PriorityResponse() {
    }
    
    public PriorityResponse(String status, String requestId) {
        this.status = status;
        this.requestId = requestId;
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

