package com.umidity.api;

import com.umidity.Coordinates;
import com.umidity.api.response.Response;

import java.util.Vector;

/**
 * Contains all useful information about a single response.<br>
 * Generally, this should be the correct alternative for each Response type for Umidity, as this contains the really
 * essential information.<br>
 *
 * However, sometimes it may be useful to directly use the "raw" response classes, in which case you should visit the
 * OpenWeather official documentation as some part of the code is left undocumented to avoid redundancy.
 *
 */
public class Single extends Response {
    private boolean isReadOnly;

    /**
     * Creates a new Single object in ReadWrite mode.
     */
    public Single(){
        isReadOnly = false;
    }

    /**
     * Creates a new Single object from a ApiResponse, in ReadOnly mode.
     * @param response
     */
    public Single(Response response){
        isReadOnly = true;
        humidity = response.getHumidity();
        temp = response.getTemp();
        cityName = response.getCityName();
        cityId = response.getCityId();
        coord = response.getCoord();
        cityCountry = response.getCityCountry();
        timestamp = response.getTimestamp();
    }

    /**
     * Returns an array og Single objects from an array of ApiResponses
     * @param response
     * @return
     */
    public static Single[] getSingles(Response[] response){
        Vector<Single> singles = new Vector<>();
        for(Response r: response){
            singles.add(new Single(r));
        }
        return singles.toArray(Single[]::new);
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    /**
     * Sets the instance in ReadOnly mode. This will disable all setters of the instance, but getters will still work.
     * Calling a setter while in ReadOnly mode will result in IsReadOnlyException throw.
     */
    public void lock(){ isReadOnly = true;}
    /**
     * Sets the instance in ReadWrite mode.
     */
    public void unlock(){ isReadOnly = false;}

    /**
     * Set humidity value.
     * @param humidity
     * @throws IsReadOnlyException if the object was previosly locked
     */
    public void setHumidity(int humidity) throws IsReadOnlyException {
        if(isReadOnly) throw new IsReadOnlyException();
        this.humidity = humidity;
    }
    /**
     * Set timestamp
     * @param timestamp
     * @throws IsReadOnlyException if the object was previosly locked
     */
    public void setTimestamp(long timestamp)throws IsReadOnlyException {
        if(isReadOnly) throw new IsReadOnlyException();
        this.timestamp = timestamp;
    }
    /**
     * Set temperature
     * @param temp
     * @throws IsReadOnlyException if the object was previosly locked
     */
    public void setTemp(double temp)throws IsReadOnlyException {
        if(isReadOnly) throw new IsReadOnlyException();
        this.temp = temp;
    }
    /**
     * Set city name
     * @param cityName
     * @throws IsReadOnlyException if the object was previosly locked
     */
    public void setCityName(String cityName)throws IsReadOnlyException {
        if(isReadOnly) throw new IsReadOnlyException();
        this.cityName = cityName;
    }
    /**
     * Set city id
     * @param cityId
     * @throws IsReadOnlyException if the object was previosly locked
     */
    public void setCityId(int cityId)throws IsReadOnlyException {
        if(isReadOnly) throw new IsReadOnlyException();
        this.cityId = cityId;
    }
    /**
     * Set city zip code
     * @param cityZipCode
     * @throws IsReadOnlyException if the object was previosly locked
     */
    public void setCityZipCode(String cityZipCode) throws IsReadOnlyException{
        if(isReadOnly) throw new IsReadOnlyException();
        this.cityZipCode = cityZipCode;
    }
    /**
     * Set city country
     * @param cityCountry
     * @throws IsReadOnlyException if the object was previosly locked
     */
    public void setCityCountry(String cityCountry)throws IsReadOnlyException {
        if(isReadOnly) throw new IsReadOnlyException();
        this.cityCountry = cityCountry;
    }
    /**
     * Set coordinates
     * @param coord
     * @throws IsReadOnlyException
     */
    public void setCoord(Coordinates coord) throws IsReadOnlyException{
        if(isReadOnly) throw new IsReadOnlyException();
        this.coord = coord;
    }

}
