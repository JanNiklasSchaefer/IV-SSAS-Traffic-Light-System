package de.tub.aot.timeService;

public class GlobalTimer {

    long validationTimer;

    public GlobalTimer() {
        this.validationTimer = System.currentTimeMillis();
    }

    public boolean validateTime(long timer){

        //Window of 2000 ms to allow messages TODO: Adjust timer
        int range = 200000;

        if(validationTimer + range > timer && validationTimer - range < timer) {
            return true;
        }

        return false;
    }

}
