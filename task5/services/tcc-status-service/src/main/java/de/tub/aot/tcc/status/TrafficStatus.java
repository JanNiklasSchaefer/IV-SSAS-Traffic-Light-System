package de.tub.aot.tcc.status;

public class TrafficStatus {

    private String state;
    private String timestamp;

    public TrafficStatus() {
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
