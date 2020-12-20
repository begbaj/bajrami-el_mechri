package umidity.api.response;

public class MainResponse implements IHumidity {
    public float temp;
    public float feels_like;
    public float pressure;
    public int humidity;
    public float temp_min;
    public float temp_max;
    public float sea_level;
    public float grnd_level;


    @Override
    public int getHumidity() {
        return humidity;
    }
}
