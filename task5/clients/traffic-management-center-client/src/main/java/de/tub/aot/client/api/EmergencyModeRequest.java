package de.tub.aot.client.api;

public class EmergencyModeRequest {

    private String operator;
    private String reason; // "accident", "protest", "maintenance", "fire"
    private String affectedArea; // intersection IDs or area name
    private boolean allRed; // set all lights to red
    private int durationMinutes; // how long should emergency mode last

    public EmergencyModeRequest() {
    }

    public EmergencyModeRequest(String operator, String reason, String affectedArea, boolean allRed,
            int durationMinutes) {
        this.operator = operator;
        this.reason = reason;
        this.affectedArea = affectedArea;
        this.allRed = allRed;
        this.durationMinutes = durationMinutes;
    }

    // Getters and Setters
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAffectedArea() {
        return affectedArea;
    }

    public void setAffectedArea(String affectedArea) {
        this.affectedArea = affectedArea;
    }

    public boolean isAllRed() {
        return allRed;
    }

    public void setAllRed(boolean allRed) {
        this.allRed = allRed;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}
