package de.tub.aot.common.models;

/**
 * Represents a GPS coordinate in Degrees, Minutes, Seconds (DMS) format.
 * Based on WGS84 coordinate system.
 * 
 * @see <a href=
 *      "https://en.wikipedia.org/wiki/Geographic_coordinate_system">Geographic
 *      Coordinate System</a>
 * @see <a href=
 *      "https://en.wikipedia.org/wiki/World_Geodetic_System#WGS84">WGS84</a>
 */
public class GPSCoordinate {

    private int degrees;
    private int minutes;
    private double seconds;
    private boolean isLatitude; // true for latitude, false for longitude

    public GPSCoordinate() {
    }

    /**
     * Creates a GPS coordinate in DMS format.
     * 
     * @param degrees    Degrees component (0-90 for latitude, 0-180 for longitude)
     * @param minutes    Minutes component (0-59)
     * @param seconds    Seconds component (0-59.999...)
     * @param isLatitude true if this is a latitude coordinate, false for longitude
     * @throws IllegalArgumentException if values are out of valid range
     */
    public GPSCoordinate(int degrees, int minutes, double seconds, boolean isLatitude) {
        this.isLatitude = isLatitude;
        this.setDegrees(degrees);
        this.setMinutes(minutes);
        this.setSeconds(seconds);
    }

    public int getDegrees() {
        return degrees;
    }

    public void setDegrees(int degrees) {
        if (isLatitude) {
            if (degrees < -90 || degrees > 90) {
                throw new IllegalArgumentException("Latitude degrees must be between -90 and 90");
            }
        } else {
            if (degrees < -180 || degrees > 180) {
                throw new IllegalArgumentException("Longitude degrees must be between -180 and 180");
            }
        }
        this.degrees = degrees;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        if (minutes < 0 || minutes >= 60) {
            throw new IllegalArgumentException("Minutes must be between 0 and 59");
        }
        this.minutes = minutes;
    }

    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        if (seconds < 0.0 || seconds >= 60.0) {
            throw new IllegalArgumentException("Seconds must be between 0 and 59.999...");
        }
        this.seconds = seconds;
    }

    public boolean isLatitude() {
        return isLatitude;
    }

    public void setLatitude(boolean isLatitude) {
        this.isLatitude = isLatitude;
    }

    /**
     * Converts DMS format to decimal degrees.
     * 
     * @return decimal degrees representation
     */
    public double toDecimalDegrees() {
        double sign = degrees >= 0 ? 1.0 : -1.0;
        return sign * (Math.abs(degrees) + minutes / 60.0 + seconds / 3600.0);
    }

    /**
     * Creates a GPSCoordinate from decimal degrees.
     * 
     * @param decimalDegrees decimal degrees value
     * @param isLatitude     true if this is a latitude coordinate, false for
     *                       longitude
     * @return GPSCoordinate in DMS format
     */
    public static GPSCoordinate fromDecimalDegrees(double decimalDegrees, boolean isLatitude) {
        double absDegrees = Math.abs(decimalDegrees);
        int deg = (int) absDegrees;
        double minutesDecimal = (absDegrees - deg) * 60.0;
        int min = (int) minutesDecimal;
        double sec = (minutesDecimal - min) * 60.0;

        GPSCoordinate coord = new GPSCoordinate(deg, min, sec, isLatitude);
        if (decimalDegrees < 0) {
            coord.setDegrees(-deg);
        }
        return coord;
    }

    @Override
    public String toString() {
        String direction;
        if (isLatitude) {
            direction = degrees >= 0 ? "N" : "S";
        } else {
            direction = degrees >= 0 ? "E" : "W";
        }
        // Format seconds to remove trailing zeros if possible
        String secondsStr = seconds == (int) seconds ? String.format("%d", (int) seconds)
                : String.format("%.3f", seconds).replaceAll("0+$", "").replaceAll("\\.$", "");
        return String.format("%d°%d'%s\"%s", Math.abs(degrees), minutes, secondsStr, direction);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        GPSCoordinate that = (GPSCoordinate) obj;
        return degrees == that.degrees &&
                minutes == that.minutes &&
                Double.compare(that.seconds, seconds) == 0 &&
                isLatitude == that.isLatitude;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(degrees, minutes, seconds, isLatitude);
    }
}
