package umidity.database;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import umidity.api.response.Coordinates;

import java.util.Date;

/**
 * Inserire documentazione
 */
public class HumidityRecord {

    private float humidity;
    private int cityId;

    private Date date;
    private Coordinates location;

    //TODO: Documentazione


    public float getHumidity() { return humidity; }
    public Date getDate() { return date; }
    public Coordinates getLocation() { return location; }
    public int getCityId() { return cityId; }

    @JsonCreator //JSON creator?? cosa fa?
    public HumidityRecord(@JsonProperty("humidity") float humidity,
                          @JsonProperty("date") Date date,
                          @JsonProperty("city_id") int cityId,
                          @JsonProperty("location") Coordinates location) {
        this.humidity = humidity;
        this.date = date;
        this.cityId = cityId;
        this.location = location;
    }

    @Override
    public String toString(){
        //return "humidity:" + humidity +" date:" +date+ " " + location.toString();
        //Ai fini di rendere il codice più intuitivo:
        return String.valueOf(humidity);
    }
}
