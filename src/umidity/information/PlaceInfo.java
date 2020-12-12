package umidity.information;

public class PlaceInfo implements Information, Coordinates{
    String city;
    String state;
    String zipcode;

    float lat;
    float lon;

    public String toString(){
        return "city:" + city + " state:" + state + " zipcode:"+zipcode;
    }
}
