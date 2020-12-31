package com.umidity.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OneCallResponse extends OneCall{

    //region Properties
    @JsonProperty("timezone")
    protected String timezone;
    @JsonProperty("timezone_offset")
    protected long timezoneOffset;

    @JsonProperty("minutely")
    protected Minutely[] minutely;
    @JsonProperty("hourly")
    protected Hourly[] hourly;
    @JsonProperty("daily")
    protected Daily[] daily;
    @JsonProperty("alerts")
    protected Alerts[] alerts;

    //endregion

    //region Classes
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Minutely{
        public long dt;
        public Number precipitation;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hourly{
        public long dt;
        public Number temp;
        public Number feels_like;
        public Number pressure;
        public Number humidity;
        public Number dew_point;
        public Number uvi;
        public Number clouds;
        public RainSnow rain;
        public RainSnow snow;
        public Number visibility;
        public Number wind_speed;
        public Number wind_deg;
        public Weather[] weather;
        public Number pop;

    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Daily{
        public long dt;
        public long sunrise;
        public long sunset;
        public Temp temp;
        public Temp feels_like;
        public int pressure;
        public int humidity;
        public Number dew_point;
        public Number wind_speed;
        public Number wind_deg;
        @JsonIgnore
        public RainSnow rain;
        @JsonIgnore
        public RainSnow snow;
        public Weather[] weather;
        public Number clouds;
        public Number pop;
        public Number uvi;

        public static class Temp{
            public Number day;
            public Number min;
            public Number max;
            public Number night;
            public Number eve;
            public Number morn;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Alerts{
        public String sender_name;
        public String event;
        public long start;
        public long end;
        public String description;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RainSnow{
        @JsonProperty("1h")
        public Number oneH;
    }
    //endregion

    //region Getters
    public double getFeelsLike() {
        return feelsLike;
    }
    public double getPressure() {
        return pressure;
    }
    public double getWindSpeed() {
        return windSpeed;
    }
    public double getWindDeg() {
        return windDeg;
    }
    public double getWindGust() {
        return windGust;
    }
    public String getTimezone() {
        return timezone;
    }
    public long getTimezoneOffset() {
        return timezoneOffset;
    }
    public Minutely[] getMinutely() {
        return minutely;
    }
    public Hourly[] getHourly() {
        return hourly;
    }
    public Daily[] getDaily() {
        return daily;
    }
    public Alerts[] getAlerts() {
        return alerts;
    }
    //endregion

}
