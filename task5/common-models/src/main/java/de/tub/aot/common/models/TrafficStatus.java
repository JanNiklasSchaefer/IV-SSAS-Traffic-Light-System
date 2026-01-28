package de.tub.aot.common.models;
import java.time.Instant;

/**
 * Describes the current state of a traffic light.
 * Contains at least:
 * - An identification of the traffic light (TrafficLightId)
 * - Time for the status information
 * - Status values (for individual lights at an intersection)
 */
public class TrafficStatus {

    private TrafficLightId trafficLightId;
    private Instant timestamp;
    private String state; // Overall state (for backward compatibility)
    // Map for individual lights at an intersection: direction -> state
    // e.g., "north-south" -> "green", "east-west" -> "red"
    private String pedestrianState;

    public TrafficStatus(TrafficLightId trafficLightId, Instant timestamp, String state) {
        this.trafficLightId = trafficLightId;
        this.timestamp = timestamp;
        this.state = state;
        if(state.equals("green")) this.pedestrianState = "red";
        if(state.equals("yellow")) this.pedestrianState = "yellow";
        if(state.equals("red")) this.pedestrianState = "green";
    }

    public TrafficLightId getTrafficLightId() {
        return trafficLightId;
    }

    public void setTrafficLightId(TrafficLightId trafficLightId) {
        this.trafficLightId = trafficLightId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getPedestrianState(){
        return this.pedestrianState;
    }

    /**
     * Gets the overall state (for backward compatibility).
     * 
     * @return overall state as string
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the overall state (for backward compatibility).
     * 
     * @param state overall state as string
     */
    public void setState(String state) {
        this.state = state;
        if(state.equals("green")) this.pedestrianState = "red";
        if(state.equals("yellow")) this.pedestrianState = "yellow";
        if(state.equals("red")) this.pedestrianState = "green";
    }
}
