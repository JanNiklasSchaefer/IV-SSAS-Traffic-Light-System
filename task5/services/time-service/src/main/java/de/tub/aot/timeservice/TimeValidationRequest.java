package de.tub.aot.timeservice;

public class TimeValidationRequest {
    
    private long timestamp;
    
    public TimeValidationRequest() {
    }
    
    public TimeValidationRequest(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

