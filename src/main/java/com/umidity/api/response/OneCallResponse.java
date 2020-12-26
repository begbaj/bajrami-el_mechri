package com.umidity.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.umidity.Coordinates;
import com.umidity.ICoordinates;
import com.umidity.IHumidity;
import com.umidity.api.Single;

public class OneCallResponse extends Response implements ICoordinates, IHumidity {
    public float lat;
    public float lon;
    public String timezone;
    public long timezone_offset;
    public Current current;
    public Minutely[] minutely;
    public Hourly[] hourly;
    public Daily[] daily;
    public Alerts[] alerts;

    @Override
    public int getHumidity() {
        return current.humidity;
    }

    public static class Current{
        public long dt;
        public long sunrise;
        public long sunset;
        public float temp;
        public float feels_like;
        public int pressure;
        public int humidity;
        public float dew_point;
        public int clouds;
        public int uvi;
        public int visibility;
        public int wind_speed;
        public int wind_deg;
        public Weather[] weather;
    }

    public static class Minutely{
        public long dt;
        public int precipitation;
    }

    public static class Hourly{
        public long dt;
        public float temp;
        public float feels_like;
        public int pressure;
        public int humidity;
        public float dew_point;
        public int uvi;
        public int clouds;
        public Rain rain;
        public Snow snow;
        public int visibility;
        public float wind_speed;
        public int wind_deg;
        public Weather[] weather;
        public float pop;

    }

    public static class Daily{
        public long dt;
        public long sunrise;
        public long sunset;
        public Temp temp;
        public Temp feels_like;
        public int pressure;
        public int humidity;
        public float dew_point;
        public float wind_speed;
        public int wind_deg;
        @JsonIgnore
        public Rain rain;
        @JsonIgnore
        public Snow snow;
        public Weather[] weather;
        public int clouds;
        public float pop;
        public int uvi;

        public static class Temp{
            public float day;
            public float min;
            public float max;
            public float night;
            public float eve;
            public float morn;
        }
    }

    public static class Alerts{
        public String sender_name;
        public String event;
        public long start;
        public long end;
        public String description;
    }

    public static class Rain{
        @JsonProperty("1h")
        public Number oneH;
    }
    public static class Snow{
        @JsonProperty("1h")
        public Number oneH;
    }



    @Override
    public Coordinates getCoord() {
        return new Coordinates(lat,lon);
    }

    /**
     * Get a Single object about the current weather.
     * @return
     */
    @Override
    public Single getSingle(){
        if(current != null)
            return new Single(this);
        return null;
    }

    /**
     * Get a Single object about the current weather.
     * @return
     */
    @Override
    public Single[] getSingles(){
        return null;
    }
}
