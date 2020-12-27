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

    private boolean     isReadOnly;
    private Coordinates      coord;
    private int           humidity;
    private long         timestamp;
    private float             temp;
    private String        cityName;
    private int          cityId;
    /**
     * Currently not available
     */
    private String     cityZipCode;
    private String     cityCountry;

    public Single(){
        isReadOnly = false;
    }

    public Single(ApiResponse response){
        isReadOnly = true;

        humidity = response.getHumidity();
        temp = response.main.temp;

        cityName = response.name;
        cityId = response.id;
        cityCountry = response.sys.country;
        coord = response.getCoord();


        timestamp = Long.parseLong(response.dt);

    }
    public Single(OneCallResponse response){
        isReadOnly = true;

        humidity = response.getHumidity();
        temp = response.current.temp;;
        coord = response.getCoord();

        timestamp = response.current.dt;
    }

    public static Single[] getSingles(ApiResponse[] response){
        Vector<Single> singles = new Vector<>();
        for(ApiResponse r: response){
            singles.add(new Single(r));
        }
        return (Single[]) singles.toArray();
    }

    public int getHumidity() {
        return humidity;
    }
    public long getTimestamp() {
        return timestamp;
    }
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

    public void setHumidity(int humidity) throws IsReadOnlyException {
        if(isReadOnly) throw new IsReadOnlyException();
        this.humidity = humidity;
    }
    public void setTimestamp(long timestamp)throws IsReadOnlyException {
        if(isReadOnly) throw new IsReadOnlyException();
        this.timestamp = timestamp;
    }
    public void setTemp(float temp)throws IsReadOnlyException {
        if(isReadOnly) throw new IsReadOnlyException();
        this.temp = temp;
    }
    public void setCityName(String cityName)throws IsReadOnlyException {
        if(isReadOnly) throw new IsReadOnlyException();
        this.cityName = cityName;
    }
    public void setCityId(int cityId)throws IsReadOnlyException {
        if(isReadOnly) throw new IsReadOnlyException();
        this.cityId = cityId;
    }
    public void setCityZipCode(String cityZipCode) throws IsReadOnlyException{
        if(isReadOnly) throw new IsReadOnlyException();
        this.cityZipCode = cityZipCode;
    }
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
