package com.umidity.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.umidity.Coordinates;
import com.umidity.ICoordinates;
import com.umidity.IHumidity;
import com.umidity.api.Single;

//TODO: documentazione
@JsonIgnoreProperties(ignoreUnknown = true)
public class OneCallResponse extends Response implements ICoordinates, IHumidity {

    @JsonProperty("lat")
    public float lat;
    @JsonProperty("lon")
    public float lon;
    @JsonProperty("timezone")
    public String timezone;
    @JsonProperty("timezone_offset")
    public long timezone_offset;
    @JsonProperty("current")
    public Current current;
    @JsonProperty("minutely")
    public Minutely[] minutely;
    @JsonProperty("hourly")
    public Hourly[] hourly;
    @JsonProperty("daily")
    public Daily[] daily;
    @JsonProperty("alerts")
    public Alerts[] alerts;


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
        public RainSnow rain;
        public RainSnow snow;
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
        public RainSnow rain;
        @JsonIgnore
        public RainSnow snow;
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

    public static class RainSnow{
        @JsonProperty("1h")
        public Number oneH;
    }

    @Override
    public int getHumidity() {
        return current.humidity;
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
