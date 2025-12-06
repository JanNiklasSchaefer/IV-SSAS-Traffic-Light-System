package de.tub.aot.timeservice;

public class GlobalTimer {

    private final long validationTimer;

    public GlobalTimer() {
        this.validationTimer = System.currentTimeMillis();
    }

    public boolean validateTime(long timestamp) {
        // Window of 200000 ms (200 seconds) to allow messages
        // TODO: Adjust timer range as needed
        long range = 200000;

        return (validationTimer + range > timestamp && validationTimer - range < timestamp);
    }
}

