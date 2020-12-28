package com.umidity.api.caller;

import com.fasterxml.jackson.databind.*;
import com.umidity.api.Single;
import com.umidity.api.response.ApiResponse;
import com.umidity.api.response.ForecastResponse;
import com.umidity.api.response.OneCallHistoricalResponse;
import com.umidity.api.response.OneCallResponse;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.EnumSet;

/**
 * This class handles every connection to the API.
 */
public class ApiCaller extends Caller {

    /**
     * Api key used for making api calls
     */
    private final String    appid;
    private EMode           emode;
    private EUnits         eunits;
    private String      endParams;


    /**
     * Api caller constructor.
     * @param appid Api key.
     * @param eunits Metric, Imperial
     */
    public ApiCaller(String appid, EUnits eunits){
        this.appid = appid;
        this.emode = EMode.JSON;
        this.eunits = eunits;
        updateEndParams();
    }
    /**
     * Api caller constructor
     * @param appid Api key
     */
    @Deprecated
    public ApiCaller(String appid, EMode emode, EUnits eunits){
        this.appid = appid;
        this.emode = emode;
        this.eunits = eunits;
        updateEndParams();
    }

    /**
     * Update ending parameters for api calls.
     */
    private void updateEndParams(){
        this.endParams = "&appid=" + getAppid();
        //if(EMode != EMode.JSON) this.endParams += "&mode=" + EMode;
        if(eunits != eunits.Standard) this.endParams += "&units=" + eunits;
    }

    //region OneCall methods
    /**
     * Make a <em>oneCall</em> call to api.
     * @param lat latitude
     * @param lon longitude
     * @param excludes objects to exclude from response (see openweather documentation for more info)
     * @return
     * @throws IOException
     */
    public OneCallResponse oneCall(float lat, float lon, EnumSet<EExclude> excludes) throws IOException{

        boolean current = true;
        if(excludes.contains(EExclude.current)) current = false;
        boolean forecast = true;
        if(!excludes.containsAll(Arrays.asList(new EExclude[]{EExclude.minutely, EExclude.hourly, EExclude.daily})))
            forecast = false;

        for(ApiListener l:apiListeners){
            l.onRequest(this, null);
            if(current) l.onRequestCurrent(this, null);
            if(forecast) l.onRequestForecast(this, null);
        }

        StringBuilder url = new StringBuilder("https://api.openweathermap.org/data/2.5/onecall?");
        url.append("lat=" + lat + "&lon=" + lon);
        if(excludes.size() > 0){
            url.append("&exclude=");
            boolean first = true;
            if(excludes.contains(EExclude.current)){ url.append("current"); first = false;}
            if(excludes.contains(EExclude.minutely)){ if(!first) url.append(","); url.append("minutely"); first = false;}
            if(excludes.contains(EExclude.hourly)){ if(!first) url.append(","); url.append("hourly"); first = false;}
            if(excludes.contains(EExclude.daily)){ if(!first) url.append(","); url.append("daily"); first = false;}
            if(excludes.contains(EExclude.alerts)){ if(!first) url.append(","); url.append("alerts"); first = false;}
        }
        url.append(endParams);

        var response = new ObjectMapper().readValue(new URL(url.toString()), OneCallResponse.class);
        var r = new ApiArgument(response.getSingles());
        for(ApiListener l:apiListeners){
            if(response != null);{
                if(current) l.onReceiveCurrent(this, r );
                if(forecast) l.onReceiveForecast(this,r);
                l.onReceive(this, r);
            }
        }
        return response;
    }
    /**
     * Make a <em>oneCall</em> call to api for historical data.
     * @param lat latitude
     * @param lon longitude
     * @param dt timestamp (see openweather documentation for more info)
     * @return
     * @throws IOException
     */
    public OneCallHistoricalResponse oneCall(float lat, float lon, long dt) throws IOException{
        for(ApiListener l:apiListeners){
            l.onRequest(this, null);
            l.onRequestHistorical(this, null);
        }

        StringBuilder url = new StringBuilder("https://api.openweathermap.org/data/2.5/onecall/timemachine?");
        url.append("lat=" + lat + "&lon=" + lon + "&dt=" + dt);
        url.append(endParams);
        var response = new ObjectMapper().readValue(new URL(url.toString()), OneCallHistoricalResponse.class);
        var apiArg = new ApiArgument(response.getSingles());
        for(ApiListener l:apiListeners){
            if(response != null);{
                l.onReceive(this, apiArg);
                l.onReceiveHistorical(this, apiArg);
            }
        }
        return response;
    }
    //endregion

    //region ApiResponse methods
    /**
     * Get ApiResponse by city name
     * @param cityName REQUIRED: city name
     * @param stateCode OPTIONAL: state code
     * @param countryCode OPTIONAL: country code
     */
    public ApiResponse getByCityName(String cityName, String stateCode, String countryCode) throws IOException {
        for(ApiListener l:apiListeners){
            l.onRequest(this, null);
            l.onRequestCurrent(this, null);
        }

        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + cityName
                + (!stateCode.equals("") ? "," + stateCode : "")
                + (!countryCode.equals("") ? "," + countryCode : "")
                + endParams;

        var response = new ObjectMapper().readValue(new URL(url), ApiResponse.class);
        var apiArg = new ApiArgument(response.getSingles());
        for(ApiListener l:apiListeners){
            l.onReceiveCurrent(this, apiArg);
            l.onReceive(this, apiArg);
        }
        return response;
    }
    /**
     * Get ApiResponse by a single city id
     * @param cityId city id
     */
    public ApiResponse getByCityId(String cityId) throws IOException  {
        for(ApiListener l:apiListeners){
            l.onRequest(this, null);
            l.onRequestCurrent(this, null);
        }
        String url = "https://api.openweathermap.org/data/2.5/weather?id=" + cityId + endParams;
        ApiResponse response = new ObjectMapper().readValue(new URL(url), ApiResponse.class);
        var apiArg = new ApiArgument(response.getSingles());

        for(ApiListener l:apiListeners){
            l.onReceiveCurrent(this, apiArg);
            l.onReceive(this, apiArg);
        }
        return response;
    }
    /**
     * Get ApiResponse by Coordinates
     * @param lat latitude
     * @param lon longitude
     */
    public ApiResponse getByCoordinates(float lat, float lon) throws IOException  {
        for(ApiListener l:apiListeners){
            l.onRequest(this, null);
            l.onRequestCurrent(this, null);
        }

        String url =  "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + endParams;

        var response = new ObjectMapper().readValue(new URL(url), ApiResponse.class);
        var apiArg = new ApiArgument(response.getSingles());

        for(ApiListener l:apiListeners){
            l.onReceiveCurrent(this, apiArg);
            l.onReceive(this, apiArg);
        }
        return response;
    }
    /**
     * Get ApiResponse by Zip Code
     * @param zipCode REQUIRED: zip code
     * @param countryCode OPTIONAL: let this empty if no countryCode is needed
     */
    public ApiResponse getByZipCode(String zipCode, String countryCode) throws IOException  {
        for(ApiListener l:apiListeners){
            l.onRequest(this, null);
            l.onRequestCurrent(this, null);
        }

        String url = "https://api.openweathermap.org/data/2.5/weather?zip=" + zipCode
                + (!countryCode.equals("")? "," + countryCode : "" )
                + endParams;
        var response = new ObjectMapper().readValue(new URL(url), ApiResponse.class);
        var apiArg = new ApiArgument(response.getSingles());

        for(ApiListener l:apiListeners){
            l.onReceiveCurrent(this, apiArg);
            l.onReceive(this, apiArg);
        }
        return response;
    }
    //endregion

    //region ForecastResponse methods
    /**
     * Get ForecastResponse by city name
     * @param cityName REQUIRED: city name
     * @param stateCode OPTIONAL: state code
     * @param countryCode OPTIONAL: country code
     */
    public ForecastResponse getForecastByCityName(String cityName, String stateCode, String countryCode) throws IOException {
        for(ApiListener l:apiListeners){
            l.onRequest(this, null);
            l.onRequestForecast(this, null);
        }

        String url = "https://api.openweathermap.org/data/2.5/forecast?q="
                + cityName
                + (!stateCode.equals("") ? "," + stateCode : "")
                + (!countryCode.equals("") ? "," + countryCode : "")
                + endParams;

        var response = new ObjectMapper().readValue(new URL(url), ForecastResponse.class);
        var apiArg = new ApiArgument(response.getSingles());


        for(ApiListener l:apiListeners){
            l.onReceiveCurrent(this, apiArg);
            l.onReceive(this, apiArg);
        }
        return response;
    }
    /**
     * Get ForecastResponse by a single city id
     * @param cityId city id
     */
    public ForecastResponse getForecastByCityId(String cityId) throws IOException  {
        for(ApiListener l:apiListeners){
            l.onRequest(this, null);
            l.onRequestForecast(this, null);
        }

        String url = "https://api.openweathermap.org/data/2.5/forecast?id=" + cityId + endParams;
        var response = new ObjectMapper().readValue(new URL(url), ForecastResponse.class);
        var apiArg = new ApiArgument(response.getSingles());


        for(ApiListener l:apiListeners){
            l.onReceiveCurrent(this, apiArg);
            l.onReceive(this, apiArg);
        }
        return response;
    }
    /**
     * Get ForecastResponse by Coordinates
     * @param lat latitude
     * @param lon longitude
     */
    public ForecastResponse getForecastByCoordinates(float lat, float lon) throws IOException  {
        for(ApiListener l:apiListeners){
            l.onRequest(this, null);
            l.onRequestForecast(this, null);
        }

        String url =  "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + endParams;

        var response = new ObjectMapper().readValue(new URL(url), ForecastResponse.class);
        var apiArg = new ApiArgument(response.getSingles());


        for(ApiListener l:apiListeners){
            l.onReceiveCurrent(this, apiArg);
            l.onReceive(this, apiArg);
        }
        return response;
    }
    /**
     * Get ForecastResponse by Zip Code
     * @param zipCode REQUIRED: zip code
     * @param countryCode OPTIONAL: let this empty if no countryCode is needed
     */
    public ForecastResponse getForecastByZipCode(String zipCode, String countryCode) throws IOException  {
        for(ApiListener l:apiListeners){
            l.onRequest(this, null);
            l.onRequestForecast(this, null);
        }

        String url = "https://api.openweathermap.org/data/2.5/forecast?zip=" + zipCode
                + (!countryCode.equals("")? "," + countryCode : "" )
                + endParams;

        var response = new ObjectMapper().readValue(new URL(url), ForecastResponse.class);
        var apiArg = new ApiArgument(response.getSingles());

        for(ApiListener l:apiListeners){
            l.onReceiveCurrent(this, apiArg);
            l.onReceive(this, apiArg);
        }
        return response;
    }
    //endregion

    //region Getters and Setters
    /**
     * Get api key used by the ApiCaller
     * @return
     */
    public String getAppid(){ return appid; }

    /**
     * Get response Mode (format of api response) set for this ApiCaller
     * (!!! It should always be JSON, since Umidity parses api responses with Jackson)
     * @return
     */
    public EMode getMode(){ return emode; }
    /**
     * Get the response units set for this ApiCaller
     * @return
     */
    public EUnits getUnit(){ return eunits; }

    /**
     * Set response Mode
     * @param value
     */
    public void setMode(EMode value){ emode = value; updateEndParams(); }
    /**
     * Set response units
     * @param value
     */
    public void setUnit(EUnits value){ eunits = value; updateEndParams(); }
    //endregion

}
