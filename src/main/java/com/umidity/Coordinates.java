package com.umidity;
import java.util.Objects;

/**
 * Geo location coordinates.
 */
public class Coordinates {

    /**
     * Latitude
     */
    public float lat;
    /**
     * Longitude
     */
    public float lon;

    public Coordinates(){}
    public Coordinates(double lat, double lon){
        this.lat = Float.parseFloat(String.valueOf(lat));
        this.lon = Float.parseFloat(String.valueOf(lon));
    }

    public void setLat(double lat){
        this.lat = Float.parseFloat(String.valueOf(lat));
    }
    public void setLon(double lon){
        this.lon = Float.parseFloat(String.valueOf(lon));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Float.compare(that.lat, lat) == 0 &&
                Float.compare(that.lon, lon) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }
}
