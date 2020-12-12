package umidity.information;
import java.sql.Time;
import java.time.LocalDate;

public class WeatherInfo implements Information{
    double humidity;
    LocalDate timestamp;
    PlaceInfo location;

    public WeatherInfo(double humidity, LocalDate timestamp, PlaceInfo location){
        this.humidity=humidity;
        this.timestamp=timestamp;
        this.location=location;
    }
    public Double getHumidity() {
        return humidity;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public PlaceInfo getLocation() {
        return location;
    }

    public String toString(){
        return "Humidity:" + humidity + "  Time:" + timestamp + " " +location.toString();
    }

}
