package com.umidity.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.umidity.Coordinates;
import com.umidity.ICoordinates;
import com.umidity.IHumidity;
import com.umidity.api.Single;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OneCallHistoricalResponse extends OneCall {

    @JsonProperty("hourly")
    public Hourly[] hourly;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hourly{
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

    public Hourly[] getHourly(){ return hourly;}
}
