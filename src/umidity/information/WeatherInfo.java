package umidity.information;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Deprecated
public class WeatherInfo implements Information{
    double humidity;
    SimpleDateFormat timestamp;
    PlaceInfo location;

    public WeatherInfo(double humidity, SimpleDateFormat timestamp, PlaceInfo location){
        this.humidity=humidity;
        this.timestamp=timestamp;
        this.location=location;
    }
    public Double getHumidity() {
        return humidity;
    }

    public SimpleDateFormat getTimestamp() {
        return timestamp;
    }

    public PlaceInfo getLocation() {
        return location;
    }

    public String toString(){
        return "Humidity:" + humidity + "  Time:" + timestamp + " " +location.toString();
    }

}
