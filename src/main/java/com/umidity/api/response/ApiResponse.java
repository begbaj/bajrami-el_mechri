package com.umidity.api.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.umidity.Coordinates;
import com.umidity.ICoordinates;
import com.umidity.IHumidity;
import com.umidity.api.Single;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse  implements Response, ICoordinates, IHumidity {

    public class Main implements IHumidity{
        /**
         * Temperature
         */
        public float temp;
        /**
         *
         */
        public float temp_kf;
        /**
         * This temperature parameter accounts for the human perception of weather
         */
        public float feels_like;
        /**
         * Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
         */
        public float pressure;
        /**
         * Humidity in %
         */
        public int humidity;
        /**
         * Minimum temperature at the moment. This is minimal currently observed temperature (within large megalopolises and urban areas)
         */
        public float temp_min;
        /**
         * Maximum temperature at the moment. This is maximal currently observed temperature (within large megalopolises and urban areas)
         */
        public float temp_max;
        /**
         * Atmospheric pressure on the sea level, hPa
         */
        public float sea_level;
        /**
         * Atmospheric pressure on the ground level, hPa
         */
        public float grnd_level;

        @Override
        public int getHumidity() {
            return humidity;
        }
    }


    /**
     * City geo location
     */
    @JsonProperty("coord")
    public Coordinates coord;
    /**
     * more info Weather condition codes
     */
    @JsonProperty("weather")
    public Weather[]   weather;
    /**
     * Internal parameter
     */
    @JsonProperty("base")
    public String       base;
    /**
     * Main information
     */
    @JsonProperty("main")
    public Main         main;
    /**
     * Wind information
     */
    @JsonProperty("wind")
    public Wind         wind;
    /**
     * Cloudiness information
     */
    @JsonProperty("clouds")
    public Clouds       clouds;
    /**
     * Rain information
     */
    @JsonProperty("rain")
    public RainSnow     rain;
    /**
     * Snow information
     */
    @JsonProperty("snow")
    public RainSnow     snow;
    /**
     *  Time of data calculation, unix, UTC
     */
    @JsonProperty("dt")
    public String       dt;
    /**
     * System information
     */
    @JsonProperty("sys")
    public Sys          sys;
    /**
     * Shift in seconds from UTC
     */
    @JsonProperty("timezone")
    public int          timezone;
    /**
     *  City ID
     */
    @JsonProperty("id")
    public int          id;
    /**
     * City name
     */
    @JsonProperty("name")
    public String       name;
    /**
     * Internal parameter
     */
    @JsonProperty("cod")
    public int          cod;

    @JsonProperty("visibility")
    public int          visibility;
    @JsonProperty("pop")
    public String       pop;
    @JsonProperty("dt_txt")
    public String       dt_txt;


    /**
     * Wind information
     */
    public class Wind{
        /**
         * Wind speed.
         */
        public float speed;
        /**
         * Wind direction, degrees.
         */
        public float deg;
        /**
         *  Wind gust.
         */
        public float gust;
    }

    /**
     * System internal parameters
     */
    public class Sys{
        /**
         * Internal parameter
         */
        public int type;
        /**
         * Internal parameter
         */
        public int id;
        /**
         * Internal parameter
         */
        public float message;
        /**
         * Country code (GB, JP etc.)
         */
        public String country;
        /**
         * Sunrise time, unix, UTC
         */
        public long sunrise;
        /**
         * Sunset time, unix, UTC
         */
        public long sunset;
        public String pod;
    }

    /**
     * Rain/Snow volume (mm).
     */
    public class RainSnow{
        /**
         * Last hour
         */
        @JsonProperty("1h")
        public float oneH;
        /**
         * Last 3 hours
         */
        @JsonProperty("3h")
        public float threeH;
    }

    /**
     * Cloudiness
     */
    public class Clouds{
        /**
         * Cloudiness in %
         */
        public int all;
    }

    @Override
    @JsonIgnore
    public Coordinates getCoord() {
        return coord;
    }

    @Override
    @JsonIgnore
    public int getHumidity() {
        return main.humidity;
    }

    /**
     * Get a Single Object about the current weather
     * @return
     */
    @Override
    @JsonIgnore
    public Single getSingle(){
        return new Single(this);
    }

    @Override
    @JsonIgnore
    public Single[] getSingles(){
        return Single.getSingles(new ApiResponse[]{this});
    }
}
