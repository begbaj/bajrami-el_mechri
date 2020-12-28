package com.umidity.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.umidity.Coordinates;

import java.util.Map;

public abstract class OneCall extends Response {
    protected double feelsLike;
    protected double pressure;
    protected double windSpeed;
    protected double windDeg;
    protected double windGust;

    @JsonProperty("current")
    public void unpackCurrent(Map<String, Object> map){
        if(map.get("dt") != null) this.timestamp = Integer.parseInt(String.valueOf(map.get("dt")));
        if(map.get("sunrise") != null) this.sunrise = Integer.parseInt(String.valueOf(map.get("sunrise")));
        if(map.get("sunset") != null) this.sunset = Integer.parseInt(String.valueOf(map.get("sunset")));
        if(map.get("temp") != null) this.temp = Integer.parseInt(String.valueOf(map.get("temp")));
        if(map.get("feels_like") != null) this.feelsLike = Integer.parseInt(String.valueOf(map.get("feels_like")));
        if(map.get("humidity") != null) this.humidity = Integer.parseInt(String.valueOf(map.get("humidity")));
        if(map.get("pressure") != null) this.pressure = Double.parseDouble(String.valueOf(map.get("pressure")));
        if(map.get("wind_gust") != null) this.windGust = Double.parseDouble(String.valueOf(map.get("wind_gust")));
        if(map.get("wind_speed") != null) this.windSpeed = Double.parseDouble(String.valueOf(map.get("wind_speed")));
        if(map.get("wind_deg") != null) this.windDeg = Double.parseDouble(String.valueOf(map.get("wind_deg")));
        //unpackWeather((Map<String, Object>)map.get("weather"));
    }

    @JsonProperty("lat")
    public void setLat(Number lat){
        if(coord == null) coord = new Coordinates();
        coord.setLat((double)lat);
    }
    @JsonProperty("lon")
    public void setLon(Number lon){
        if(coord == null) coord = new Coordinates();
        coord.setLon((double)lon);
    }
}
