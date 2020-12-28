package com.umidity.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umidity.Coordinates;
import com.umidity.ICoordinates;
import com.umidity.IHumidity;
import com.umidity.api.Single;

/**
 * Interface for Response classes
 */
public abstract class Response implements ICoordinates, IHumidity {

    protected Coordinates coord;
    protected int humidity;
    protected long timestamp;
    protected double temp;
    protected String cityName;
    protected int cityId;
    protected String cityZipCode;
    protected String cityCountry;

    /**
     * City coordinates (Latitude and Longitude)
     */
    @JsonIgnore
    public Coordinates getCoord() {
        return coord;
    }
    /**
     * Get humidity level in %
     */
    @JsonIgnore
    public int getHumidity() {
        return humidity;
    }
    /**
     *  Time of data calculation, unix, UTC
     */
    @JsonIgnore
    public long getTimestamp() {
        return timestamp;
    }
    /**
     * Get current temperature (value based on the units set in the Api Caller)
     */
    @JsonIgnore
    public double getTemp() {
        return temp;
    }
    /**
     * City name
     */
    @JsonIgnore
    public String getCityName() {
        return cityName;
    }
    /**
     * City ID
     */
    @JsonIgnore
    public int getCityId() {
        return cityId;
    }
    /**
     * City zip code
     * @return
     */
    @JsonIgnore
    public String getCityZipCode() {
        return cityZipCode;
    }
    /**
     * Country code (GB, JP etc.)
     */
    @JsonIgnore
    public String getCityCountry() {
        return cityCountry;
    }

    /**
     * Get a Single object representation of this object
     * @return
     */
    @JsonIgnore
    public Single getSingle(){
        return new Single(this);
    }

    /**
     * Get a Single objects array from this object
     * @return
     */
    @JsonIgnore
    public Single[] getSingles(){
        return new Single[]{getSingle()};
    }
}
