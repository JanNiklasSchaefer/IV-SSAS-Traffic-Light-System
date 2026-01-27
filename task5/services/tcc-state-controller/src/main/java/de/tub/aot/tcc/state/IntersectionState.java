package de.tub.aot.tcc.state;

public class IntersectionState {

    private String intersectionId;
    private String state; // "green", "yellow", "red", "emergency", "maintenance"
    private String direction; // "north-south", "east-west", "all"
    private String operator;
    private String timestamp;
    private String reason; // "emergency", "maintenance", "manual_override"

    public IntersectionState() {
    }

    public IntersectionState(String intersectionId, String state, String direction, String operator, String reason) {
        this.intersectionId = intersectionId;
        this.state = state;
        this.direction = direction;
        this.operator = operator;
        this.reason = reason;
        this.timestamp = java.time.Instant.now().toString();
    }

    // Getters and Setters
    public String getIntersectionId() {
        return intersectionId;
    }

    public void setIntersectionId(String intersectionId) {
        this.intersectionId = intersectionId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
