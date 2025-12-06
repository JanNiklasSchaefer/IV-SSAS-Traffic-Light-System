package de.tub.aot.client.api;

public class TrafficStatus {
    
    private String state;
    private String timestamp;
    
    public TrafficStatus() {
    }
    
    public TrafficStatus(String state, String timestamp) {
        this.state = state;
        this.timestamp = timestamp;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

