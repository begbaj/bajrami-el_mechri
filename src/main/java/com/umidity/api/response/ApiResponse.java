package com.umidity.api.response;


import com.fasterxml.jackson.annotation.*;
import com.umidity.Coordinates;
import com.umidity.ICoordinates;
import com.umidity.IHumidity;
import com.umidity.api.Single;

import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse extends Response {

    //region Properties
    /**
     * more info Weather condition codes
     */
    @JsonProperty("weather")
    public Weather[] weather;

    protected double feels_like;
    protected int pressure;
    protected double temp_min;
    protected double temp_max;
    protected double sea_level;
    protected double grnd_level;
    protected double windSpeed;
    protected double windDeg;
    protected double windGust;
    protected double snow1h;
    protected double snow3h;
    protected double rain1h;
    protected double rain3h;

    protected long sunrise;
    protected long sunset;

    protected int timezone;

    //endregion

    //region Unpackers
    @SuppressWarnings("unchecked")
    @JsonProperty("coord")
    public void unpackCoord(Map<String, Object> coord){
        this.coord = new Coordinates(
                Float.parseFloat(String.valueOf(coord.get("lat"))),
                Float.parseFloat(String.valueOf(coord.get("lon"))));
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("main")
    public void unpackMain(Map<String, Object> main){
        if(main.get("temp") != null) this.temp = Double.parseDouble(String.valueOf(main.get("temp")));
        if(main.get("feels_like") != null) this.feels_like = Double.parseDouble(String.valueOf(main.get("feels_like")));
        if(main.get("temp_min") != null) this.temp_min = Double.parseDouble(String.valueOf(main.get("temp_min")));
        if(main.get("temp_max") != null) this.temp_max = Double.parseDouble(String.valueOf(main.get("temp_max")));
        if(main.get("humidity") != null) this.humidity = Integer.parseInt(String.valueOf(main.get("humidity")));
        if(main.get("pressure") != null) this.pressure = Integer.parseInt(String.valueOf(main.get("pressure")));
        if(main.get("sea_level") != null) this.sea_level = Double.parseDouble(String.valueOf(main.get("sea_level")));
        if(main.get("grnd_level") != null) this.grnd_level = Double.parseDouble(String.valueOf(main.get("grnd_level")));
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("wind")
    public void unpackWind(Map<String, Object> wind){
        if(wind.get("speed") != null) this.windSpeed = Double.parseDouble(String.valueOf(wind.get("speed")));
        if(wind.get("deg") != null) this.windDeg = Double.parseDouble(String.valueOf(wind.get("deg")));
        if(wind.get("gust") != null) this.windGust = Double.parseDouble(String.valueOf(wind.get("gust")));
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("rain")
    public void unpackRain(Map<String, Object> rain){
        if(rain.get("1h") != null) this.rain1h = Double.parseDouble(String.valueOf(rain.get("1h")));
        if(rain.get("3h") != null) this.rain3h = Double.parseDouble(String.valueOf(rain.get("3h")));
    }
    @SuppressWarnings("unchecked")
    @JsonProperty("snow")
    public void unpackSnow(Map<String, Object> snow){
        if(snow.get("1h") != null) this.snow1h = Double.parseDouble(String.valueOf(snow.get("1h")));
        if(snow.get("3h") != null) this.snow3h = Double.parseDouble(String.valueOf(snow.get("3h")));
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("sys")
    public void unpackSys(Map<String, Object> sys){
        if(sys.get("country") != null) this.cityCountry = String.valueOf(sys.get("country"));
        if(sys.get("sunrise") != null) this.sunrise = Long.parseLong(String.valueOf(sys.get("sunrise")));
        if(sys.get("sunset") != null) this.sunset = Long.parseLong(String.valueOf(sys.get("sunset")));
    }
    //endregion

    //region Setters

    @JsonSetter("dt")
    public void setTimestamp(long value) {
        timestamp = value;
    }
    @JsonSetter("timezone")
    public void setTimezone(int value){
        timezone = value;
    }
    @JsonSetter("id")
    public void setId(int value){
        cityId = value;
    }
    @JsonSetter("name")
    public void setName(String value){
        cityName = value;
    }

    public void setCoord(Coordinates coord) {
        this.coord = coord;
    }
    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }
    public void setTemp(double temp) {
        this.temp = temp;
    }
    public void setFeelsLike(double feels_like) {
        this.feels_like = feels_like;
    }
    public void setPressure(int pressure) {
        this.pressure = pressure;
    }
    public void setTempMin(double temp_min) {
        this.temp_min = temp_min;
    }
    public void setTempMax(double temp_max) {
        this.temp_max = temp_max;
    }
    public void setSeaLevelPressure(double sea_level) {
        this.sea_level = sea_level;
    }
    public void setGroundLevelPressure(double grnd_level) {
        this.grnd_level = grnd_level;
    }
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
    public void setWindDeg(double windDeg) {
        this.windDeg = windDeg;
    }
    public void setWindGust(double windGust) {
        this.windGust = windGust;
    }
    public void setSnow1h(double snow1h) {
        this.snow1h = snow1h;
    }
    public void setSnow3h(double snow3h) {
        this.snow3h = snow3h;
    }
    public void setRain1h(double rain1h) {
        this.rain1h = rain1h;
    }
    public void setRain3h(double rain3h) {
        this.rain3h = rain3h;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public void setCityCountry(String cityCountry) {
        this.cityCountry = cityCountry;
    }
    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }
    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    //endregion

    //region Getters

    /**
     * more info Weather condition codes
     */
    @JsonIgnore
    public Weather[] getWeather() {
        return weather;
    }

    /**
     * Shift in seconds from UTC
     */
    @JsonIgnore
    public int getTimezone() {
        return timezone;
    }
    /**
     * This temperature parameter accounts for the human perception of weather
     */
    @JsonIgnore
    public double getFeels_like() {
        return feels_like;
    }
    /**
     * Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
     */
    @JsonIgnore
    public int getPressure() {
        return pressure;
    }
    /**
     * Minimum temperature at the moment. This is minimal currently observed temperature (within large megalopolises and urban areas)
     */
    @JsonIgnore
    public double getTempMin() {
        return temp_min;
    }
    /**
     * Maximum temperature at the moment. This is maximal currently observed temperature (within large megalopolises and urban areas)
     */
    @JsonIgnore
    public double getTempMax() {
        return temp_max;
    }
    /**
     * Atmospheric pressure on the sea level, hPa
     */
    @JsonIgnore
    public double getSeaLevelPressure() {
        return sea_level;
    }
    /**
     * Atmospheric pressure on the ground level, hPa
     */
    @JsonIgnore
    public double getGroundLevelPressure() {
        return grnd_level;
    }

    /**
     * Wind speed.
     */
    @JsonIgnore
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * Wind direction, degrees.
     */
    @JsonIgnore
    public double getWindDeg() {
        return windDeg;
    }

    /**
     *  Wind gust.
     */
    @JsonIgnore
    public double getWindGust() {
        return windGust;
    }

    /**
     * Last hour
     */
    @JsonIgnore
    public double getSnow1h() {
        return snow1h;
    }
    /**
     * Last 3 hours
     */
    @JsonIgnore
    public double getSnow3h() {
        return snow3h;
    }

    /**
     * Last hour
     */

    @JsonIgnore
    public double getRain1h() {
        return rain1h;
    }

    /**
     * Last 3 hours
     */
    @JsonIgnore
    public double getRain3h() {
        return rain3h;
    }

    /**
     * Sunrise time, unix, UTC
     */
    @JsonIgnore
    public long getSunrise() {
        return sunrise;
    }

    /**
     * Sunset time, unix, UTC
     */
    @JsonIgnore
    public long getSunset() {
        return sunset;
    }

    //endregion

}
