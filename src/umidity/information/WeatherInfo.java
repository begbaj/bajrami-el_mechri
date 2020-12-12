package umidity.information;
import java.sql.Time;

public class WeatherInfo implements Information{
    Double humidity;
    Time timestamp;
    PlaceInfo location;


    public Double getHumidity() {
        return humidity;
    }

    public Time getTimestamp() {
        return timestamp;
    }

    public PlaceInfo getLocation() {
        return location;
    }

    public String toString(){
        return "Humidity:" + humidity + "  Time:" + timestamp + " " +location.toString();
    }

}
