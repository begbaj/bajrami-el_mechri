package umidity.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
}
