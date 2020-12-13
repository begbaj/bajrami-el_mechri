package umidity.information;

public class PlaceInfo implements Information{
    String city;
    String state;
    String zipcode;
    float lat;
    float lon;

    public PlaceInfo(String city, String state, String zipcode, float lat, float lon)
    {
        this.city=city;
        this.state=state;
        this.zipcode=zipcode;
        this.lat=lat;
        this.lon=lon;
    }
    public String toString(){
        return "city:" + city + " state:" + state + " zipcode:"+zipcode;
    }
}
