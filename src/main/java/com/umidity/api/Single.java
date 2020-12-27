package com.umidity.api;

import com.umidity.Coordinates;
import com.umidity.ICoordinates;
import com.umidity.IHumidity;
import com.umidity.api.response.*;

import java.util.Vector;

/**
 * Contains all useful information about a single response.
 */
public class Single implements IHumidity, ICoordinates {

    /**
     * Self explanatory.
     */
    private boolean     isReadOnly;
    /**
     * Self explanatory.
     */
    private Coordinates      coord;
    /**
     * Self explanatory.
     */
    private int           humidity;
    /**
     * Timestamp of when this record was calculated by Openweather
     */
    private long         timestamp;
    /**
     * Temperature.
     */
    private float             temp;
    /**
     * Self explanatory.
     */
    private String        cityName;
    /**
     * Self explanatory.
     */
    private int             cityId;
    /**
     * Self explanatory.
     * Currently not available
     */
    private String     cityZipCode;
    /**
     * Self explanatory.
     */
    private String     cityCountry;

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
    public Single(ApiResponse response){
        isReadOnly = true;

        humidity = response.getHumidity();
        temp = response.main.temp;

        cityName = response.name;
        cityId = response.id;
        coord = response.coord;
        cityCountry = response.sys.country;


        timestamp = Long.parseLong(response.dt);
    }
    /**
     * Creates a new Single object from a OneCallResponse, in ReadOnly mode.
     * @param response
     */
    public Single(OneCallResponse response){
        isReadOnly = true;

        humidity = response.getHumidity();
        temp = response.current.temp;;
        coord = response.getCoord();

        timestamp = response.current.dt;
    }

    /**
     * Returns an array og Single objects from an array of ApiResponses
     * @param response
     * @return
     */
    public static Single[] getSingles(ApiResponse[] response){
        Vector<Single> singles = new Vector<>();
        for(ApiResponse r: response){
            singles.add(new Single(r));
        }
        return singles.toArray(Single[]::new);
    }

    public int getHumidity() {
        return humidity;
    }
    /**
     * Timestamp of when this record was calculated by Openweather
     */
    public long getTimestamp() {
        return timestamp;
    }
    /**
     * Temperature.
     */
    public float getTemp() {
        return temp;
    }
    public String getCityName() {
        return cityName;
    }
    public int getCityId() { return cityId; }
    public String getCityZipCode() {
        return cityZipCode;
    }
    public String getCityCountry() {
        return cityCountry;
    }
    public boolean isReadOnly() {
        return isReadOnly;
    }

    /**
     * Sets the instance in ReadOnly mode.
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
    public void setTemp(float temp)throws IsReadOnlyException {
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

    @Override
    public Coordinates getCoord() {
        return coord;
    }

    public void setCoord(Coordinates coord) throws IsReadOnlyException{
        if(isReadOnly) throw new IsReadOnlyException();
        this.coord = coord;
    }
}
