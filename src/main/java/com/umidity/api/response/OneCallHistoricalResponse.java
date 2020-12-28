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
public class OneCallHistoricalResponse implements Response, ICoordinates, IHumidity {

    @JsonProperty("lat")
    public float lat;
    @JsonProperty("lon")
    public float lon;
    @JsonProperty("timezone")
    public String timezone;
    @JsonProperty("timezone_offset")
    public long timezone_offset;
    @JsonProperty("current")
    public Classic current;
    @JsonProperty("hourly")
    public Classic[] hourly;

    public static class Classic{
        public long dt;
        public long sunrise;
        public long sunset;
        public float temp;
        public float feels_like;
        public int pressure;
        public int humidity;
        public float dew_point;
        public int uvi;
        public int clouds;
        @JsonIgnore
        public int rain;
        @JsonIgnore
        public int snow;
        public int visibility;
        public float wind_speed;
        public float wind_gust;
        public int wind_deg;
        public Weather[] weather;
        public float pop;
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

    @Override
    public Single getSingle() {
        return null;
    }

    @Override
    public Single[] getSingles() {
        //TODO:fallo
        return new Single[0];
    }


}
