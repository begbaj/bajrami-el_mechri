package umidity.database;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import umidity.api.response.Coordinates;

import java.util.Date;


public class HumidityRecord {
    float humidity;
    Date date;
    int city_id;
    Coordinates location;

    @JsonCreator
    public HumidityRecord(@JsonProperty("humidity") float humidity,@JsonProperty("date") Date date,@JsonProperty("city_id") int city_id,@JsonProperty("location") Coordinates location) {
        this.humidity = humidity;
        this.date = date;
        this.city_id = city_id;
        this.location = location;
    }


    public float getHumidity() {
        return humidity;
    }

    public Date getDate() {
        return date;
    }

    public int getCity_id() {
        return city_id;
    }

    public Coordinates getLocation() {
        return location;
    }

    public String toString(){
        return "humidity:" + humidity +" date:" +date+ " " + location.toString();
    }
}
