package com.umidity.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.umidity.api.response.ApiIResponse;
import com.umidity.api.response.EExclude;
import com.umidity.api.response.ForecastIResponse;
import com.umidity.api.response.OneCallIResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EnumSet;

/**
 * This class handles every connection to the apis.
 */
public class ApiCaller {

    /**
     * Api key used for making api calls
     */
    private final String appid;
    private EMode EMode;
    private EUnits EUnits;
    private String endParams;

    //TODO: Lista di appid, in modo da utilizzarne diverse per non rischiare di saturare la connessione

    /**
     * Api caller
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
    public OneCallIResponse oneCall(float lat, float lon, EnumSet<EExclude> excludes)
            throws JsonProcessingException, MalformedURLException, IOException{

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

        return new ObjectMapper().readValue(new URL(url.toString()), OneCallIResponse.class);
    }

    /**
     * make a "one call" call to api
     */
    public OneCallIResponse oneCall(float lat, float lon, long dt, EnumSet<EExclude> excludes)
            throws JsonProcessingException, MalformedURLException, IOException{

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

        return new ObjectMapper().readValue(new URL(url.toString()), OneCallIResponse.class);
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
    public ApiIResponse getByCityName(String cityName, String stateCode, String countryCode)
            throws JsonProcessingException, MalformedURLException, IOException {
        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + cityName
                + (!stateCode.equals("") ? "," + stateCode : "")
                + (!countryCode.equals("") ? "," + countryCode : "")
                + endParams;

        return new ObjectMapper().readValue(new URL(url), ApiIResponse.class);
    }
    /**
     * Get ApiResponse by a single city id
     * @param cityId city id
     */
    public ApiIResponse getByCityId(String cityId)
            throws JsonProcessingException, MalformedURLException, IOException  {
        String url = "https://api.openweathermap.org/data/2.5/weather?id=" + cityId + endParams;
        return new ObjectMapper().readValue(new URL(url), ApiIResponse.class);
    }
    /**
     * Get ApiResponse by a list of city ids
     * @param cityIds String array of city ids
     */
    public ApiIResponse getByCityIds(String[] cityIds)
            throws JsonProcessingException, MalformedURLException, IOException  {
        StringBuilder url = new StringBuilder("https://api.openweathermap.org/data/2.5/weather?id=");
        boolean first = true;
        for(String s:cityIds){
            if(!first) url.append(",");
            url.append(s);
            first = false;
        }
        url.append(endParams);
        return new ObjectMapper().readValue(new URL(url.toString()), ApiIResponse.class);
    }
    /**
     * Get ApiResponse by Coordinates
     * @param lat latitude
     * @param lon longitude
     */
    public ApiIResponse getByCoordinates(float lat, float lon)
            throws JsonProcessingException, MalformedURLException, IOException  {
        String url =  "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + endParams;
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL(url), ApiIResponse.class);
    }
    /**
     * Get ApiResponse by Zip Code
     * @param zipCode REQUIRED: zip code
     * @param countryCode OPTIONAL: let this empty if no countryCode is needed
     */
    public ApiIResponse getByZipCode(String zipCode, String countryCode)
            throws JsonProcessingException, MalformedURLException, IOException  {
        String url = "https://api.openweathermap.org/data/2.5/weather?zip=" + zipCode
                + (!countryCode.equals("")? "," + countryCode : "" )
                + endParams;
        return new ObjectMapper().readValue(new URL(url), ApiIResponse.class);
    }

    /**
     * Get ForecastResponse by city name
     * @param cityName REQUIRED: city name
     * @param stateCode OPTIONAL: state code
     * @param countryCode OPTIONAL: country code
     */
    public ForecastIResponse getForecastByCityName(String cityName, String stateCode, String countryCode)
            throws JsonProcessingException, MalformedURLException, IOException {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="
                + cityName
                + (!stateCode.equals("") ? "," + stateCode : "")
                + (!countryCode.equals("") ? "," + countryCode : "")
                + endParams;

        return new ObjectMapper().readValue(new URL(url), ForecastIResponse.class);
    }
    /**
     * Get ForecastResponse by a single city id
     * @param cityId city id
     */
    public ForecastIResponse getForecastByCityId(String cityId)
            throws JsonProcessingException, MalformedURLException, IOException  {
        String url = "https://api.openweathermap.org/data/2.5/forecast?id=" + cityId + endParams;
        return new ObjectMapper().readValue(new URL(url), ForecastIResponse.class);
    }
    /**
     * Get ForecastResponse by a list of city ids
     * @param cityIds String array of city ids
     */
    public ForecastIResponse getForecastByCityIds(String[] cityIds)
            throws JsonProcessingException, MalformedURLException, IOException  {
        StringBuilder url = new StringBuilder("https://api.openweathermap.org/data/2.5/forecast?id=");
        boolean first = true;
        for(String s:cityIds){
            if(!first) url.append(",");
            url.append(s);
            first = false;
        }
        url.append(endParams);
        return new ObjectMapper().readValue(new URL(url.toString()), ForecastIResponse.class);
    }
    /**
     * Get ForecastResponse by Coordinates
     * @param lat latitude
     * @param lon longitude
     */
    public ForecastIResponse getForecastByCoordinates(float lat, float lon)
            throws JsonProcessingException, MalformedURLException, IOException  {
        String url =  "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + endParams;
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL(url), ForecastIResponse.class);
    }
    /**
     * Get ForecastResponse by Zip Code
     * @param zipCode REQUIRED: zip code
     * @param countryCode OPTIONAL: let this empty if no countryCode is needed
     */
    public ForecastIResponse getForecastByZipCode(String zipCode, String countryCode)
            throws JsonProcessingException, MalformedURLException, IOException  {
        String url = "https://api.openweathermap.org/data/2.5/forecast?zip=" + zipCode
                + (!countryCode.equals("")? "," + countryCode : "" )
                + endParams;
        return new ObjectMapper().readValue(new URL(url), ForecastIResponse.class);
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
