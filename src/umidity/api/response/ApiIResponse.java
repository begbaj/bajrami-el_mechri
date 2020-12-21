package umidity.api.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiIResponse extends Response implements IResponse, ICoordinates, IHumidity {
    //TODO: documentazione necessaria
    public Coordinates  coord;
    public Weather[]    weather;
    public String       base;
    public Main         main;
    public Wind         wind;
    public Clouds       clouds;
    public RainSnow     rain;
    public RainSnow     snow;
    public String       dt;
    public Sys          sys;
    public int          timezone;
    public int          id;
    public String       name;
    public int          cod;
    public int          visibility;
    public String       pop;
    public String       dt_txt;

    public class Main implements IHumidity{
        public float temp;
        public float temp_kf;
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

    public class Wind{
        public float speed;
        public float deg;
        public float gust;
    }

    public class Sys{
        public int type;
        public int id;
        public float message;
        public String country;
        public long sunrise;
        public long sunset;
        public String pod;
    }

    public class RainSnow{
        @JsonProperty("1h")
        public float oneH;
        @JsonProperty("3h")
        public float threeH;
    }

    public class Clouds{
        public int all;
    }

    @Override
    public Coordinates getCoord() {
        return coord.getCoord();
    }

    @Override
    public int getHumidity() {
        return main.humidity;
    }
}
