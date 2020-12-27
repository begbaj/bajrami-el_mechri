package com.umidity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Geo location coordinates.
 */
public class Coordinates implements ICoordinates {
    /**
     * Latitude
     */
    @JsonProperty("lat")
    public float lat;
    /**
     * Longitude
     */
    @JsonProperty("lon")
    public float lon;

    public Coordinates(){}
    public Coordinates(float lat, float lon){
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    @JsonIgnore
    public Coordinates getCoord() {
        try{
            return (Coordinates)this.clone();
        }catch (CloneNotSupportedException e){
            return this;
        }
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
