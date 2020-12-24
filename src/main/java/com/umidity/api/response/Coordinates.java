package com.umidity.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Coordinates implements ICoordinates{
    public float lat;
    public float lon;

    public Coordinates(){
        //costruttore vuoto, non sempre serve inizializzare i valori
    }

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
