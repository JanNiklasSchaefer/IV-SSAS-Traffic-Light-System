package de.tub.aot.common.models;

import java.util.Map;
import java.util.HashMap;

/**
 * Describes the current state of a traffic light.
 * Contains at least:
 * - An identification of the traffic light (TrafficLightId)
 * - Time for the status information
 * - Status values (for individual lights at an intersection)
 */
public class TrafficStatus {

    private TrafficLightId trafficLightId;
    private String timestamp;
    private String state; // Overall state (for backward compatibility)
    // Map for individual lights at an intersection: direction -> state
    // e.g., "north-south" -> "green", "east-west" -> "red"
    private Map<String, String> lightStates;

    public TrafficStatus() {
        this.lightStates = new HashMap<>();
    }

    public TrafficStatus(TrafficLightId trafficLightId, String timestamp) {
        this();
        this.trafficLightId = trafficLightId;
        this.timestamp = timestamp;
    }

    public TrafficLightId getTrafficLightId() {
        return trafficLightId;
    }

    public void setTrafficLightId(TrafficLightId trafficLightId) {
        this.trafficLightId = trafficLightId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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
    }

    /**
     * Gets the status map for individual lights at an intersection.
     * Key: direction (e.g., "north-south", "east-west")
     * Value: state (e.g., "green", "yellow", "red")
     * 
     * @return map of direction to state
     */
    public Map<String, String> getLightStates() {
        return lightStates;
    }

    /**
     * Sets the status map for individual lights at an intersection.
     * 
     * @param lightStates map of direction to state
     */
    public void setLightStates(Map<String, String> lightStates) {
        this.lightStates = lightStates != null ? lightStates : new HashMap<>();
    }

    /**
     * Adds or updates the state for a specific direction.
     * 
     * @param direction direction (e.g., "north-south", "east-west")
     * @param state     state (e.g., "green", "yellow", "red")
     */
    public void setLightState(String direction, String state) {
        this.lightStates.put(direction, state);
    }

    /**
     * Gets the state for a specific direction.
     * 
     * @param direction direction (e.g., "north-south", "east-west")
     * @return state or null if not set
     */
    public String getLightState(String direction) {
        return this.lightStates.get(direction);
    }
}
