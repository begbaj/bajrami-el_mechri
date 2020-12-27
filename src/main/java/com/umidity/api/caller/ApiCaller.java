package com.umidity.api.caller;

import com.fasterxml.jackson.databind.*;
import com.umidity.api.response.ApiResponse;
import com.umidity.api.response.ForecastResponse;
import com.umidity.api.response.OneCallResponse;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.EnumSet;

/**
 * This class handles every connection to the API. Set the appid
 */
public class ApiCaller extends Caller {



    /**
     * Api key used for making api calls
     */
    private final String                       appid;
    private com.umidity.api.caller.EMode  EMode;
    private com.umidity.api.caller.EUnits EUnits;
    private String                        endParams;

    //TODO: Lista di appid, in modo da utilizzarne diverse per non rischiare di saturare la connessione

    /**
     * Api caller constructor
     * @param appid Api key
     */
    public ApiCaller(String appid, EMode EMode, EUnits EUnits){
        this.appid = appid;
        this.EMode = EMode;
        this.EUnits = EUnits;
        setEndParams();
    }

    public String getAppid(){ return appid; }
    public EMode getMode(){ return EMode; }
    public EUnits getUnit(){ return EUnits; }
    public void setMode(EMode value){ EMode = value; setEndParams(); }
    public void setUnit(EUnits value){ EUnits = value; setEndParams(); }

    /**
     * make a "one call" call to api
     */
    public OneCallResponse oneCall(float lat, float lon, EnumSet<EExclude> excludes) throws IOException{

        boolean current = true;
        if(excludes.contains(EExclude.current)) current = false;
        boolean forecast = true;
        if(!excludes.containsAll(Arrays.asList(new EExclude[]{EExclude.minutely, EExclude.hourly, EExclude.daily})))
            forecast = false;

        for(ApiListener l:apiListeners){
            l.onRequest(this, new ApiArgument(null));
            if(current) l.onRequestCurrent(this, new ApiArgument(null));
            if(forecast) l.onRequestForecast(this, new ApiArgument(null));
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
        for(ApiListener l:apiListeners){
            if(response != null);{
                if(current) l.onReceiveCurrent(this, new ApiArgument<>(response));
                if(forecast) l.onReceiveForecast(this, new ApiArgument<>(response));
                l.onReceive(this, new ApiArgument<>(response));
            }
        }
        return response;
    }

    /**
     * make a "one call" call to api for historicals
     */
    public OneCallResponse oneCall(float lat, float lon, long dt, EnumSet<EExclude> excludes) throws IOException{
        for(ApiListener l:apiListeners){
            l.onRequest(this, null);
            l.onRequestHistorical(this, null);
        }

        StringBuilder url = new StringBuilder("https://api.openweathermap.org/data/2.5/onecall?");
        url.append("lat=" + lat + "&lon=" + lon + "&dt=" + dt);
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
        ApiArgument apiArg = new ApiArgument<>(response);
        for(ApiListener l:apiListeners){
            if(response != null);{
                l.onReceive(this, apiArg);
                l.onReceiveHistorical(this, apiArg);
            }
        }
        return response;
    }

    /**
     * automatically returns end parameters such as units, mode and appid
     */
    private void setEndParams(){
        this.endParams = "&appid=" + appid;
        //if(EMode != EMode.JSON) this.endParams += "&mode=" + EMode;
        if(EUnits != EUnits.Standard) this.endParams += "&units=" + EUnits;
    }

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
        ApiArgument apiArg = new ApiArgument(response);
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
        var response = new ObjectMapper().readValue(new URL(url), ApiResponse.class);
        ApiArgument apiArg = new ApiArgument(response);
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
        ApiArgument apiArg = new ApiArgument(response);
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
        ApiArgument apiArg = new ApiArgument(response);

        for(ApiListener l:apiListeners){
            l.onReceiveCurrent(this, apiArg);
            l.onReceive(this, apiArg);
        }
        return response;
    }

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
        ApiArgument apiArg = new ApiArgument(response);

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
        ApiArgument apiArg = new ApiArgument(response);

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
        ApiArgument apiArg = new ApiArgument(response);

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
        ApiArgument apiArg = new ApiArgument(response);

        for(ApiListener l:apiListeners){
            l.onReceiveCurrent(this, apiArg);
            l.onReceive(this, apiArg);
        }
        return response;
    }

//    public static String getInRectangle(){ //funziona con il boundingbox, non penso lo useremo mai
//        return = "api.openweathermap.org/data/2.5/box/city?bbox={bbox}&appid={API key}";
//        return apicall;
//    }
//    public static String getInCircle(IQuery query, String appid, IParameters params){ // non credo ci servirà mai
//        String apicall = "api.openweathermap.org/data/2.5/box/city?bbox=%s&appid=%s";
//        return apicall;
//    }


    
}
