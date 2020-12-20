package umidity.api.response;

public class Coordinates implements ICoordinates{
    public float lat;
    public float lon;

    public Coordinates(){

    }

    public Coordinates(float lat, float lon){
        this.lat = lat;
        this.lon = lon;
    }
    @Override
    public Coordinates getCoord() {
        try{
            return (Coordinates)this.clone();
        }catch (CloneNotSupportedException e){
            return this;
        }
    }
}
