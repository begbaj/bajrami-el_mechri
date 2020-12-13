package umidity.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import umidity.information.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * This class handles every connection to the apis.
 */
public class ApiCalls {
//questa classe contiene il necessario per effettuare le chiamate alle api di OpenWeather

    /**
     * Api key used for making api calls
     */
    private final String appid;
    //TODO: Lista di appid, in modo da utilizzarne diverse per non rischiare di saturare la connessione
    //TODO: potrebbe essere sensato rendere la classe statica?
    /**
     * Api caller
     * @param appid Api key
     */
    public ApiCalls(String appid){
        this.appid = appid;
    }

    /**
     * Get ApiResponse by city name
     * @param cityName city name
     * @param stateCode state code
     * @param countryCode country code
     */
    public ApiResponse getByCityName(String cityName, String stateCode, String countryCode)
            throws JsonProcessingException, MalformedURLException, IOException {
        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + cityName
                + (!stateCode.equals("") ? "," + stateCode : "")
                + (!countryCode.equals("") ? "," + countryCode : "")
                + "&appid=" + appid;

        return new ObjectMapper().readValue(new URL(url), ApiResponse.class);
    }

    /**
     * Get ApiResponse by a single city id
     * @param cityId city id
     */
    public ApiResponse getByCityId(String cityId)
            throws JsonProcessingException {
        String url = "api.openweathermap.org/data/2.5/weather?id=" + cityId + "&appid=" + appid;
        return new ObjectMapper().readValue(url, ApiResponse.class);
    }

    /**
     * Get ApiResponse by a list of city ids
     * @param cityIds String array of city ids
     */
    public ApiResponse getByCityIds(String[] cityIds)
            throws JsonProcessingException {
        StringBuilder url = new StringBuilder("api.openweathermap.org/data/2.5/weather?id=");
        boolean first = true;
        for(String s:cityIds){
            if(!first) url.append(",");
            url.append(s);
            first = false;
        }
        url.append("&appid=").append(appid);
        return new ObjectMapper().readValue(url.toString(), ApiResponse.class);
    }

    /**
     * Get ApiResponse by Coordinates
     * @param lat latitude
     * @param lon longitude
     */
    public ApiResponse getByCoordinates(float lat, float lon)
            throws JsonProcessingException {
        String url =  "api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + appid;
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(url, ApiResponse.class);
    }

    /**
     * Get ApiResponse by Zip Code
     * @param zipCode REQUIRED: zip code
     * @param countryCode OPTIONAL: let this empty if no countryCode is needed
     */
    public ApiResponse getByZipCode(String zipCode, String countryCode)
            throws JsonProcessingException {
        String url = "api.openweathermap.org/data/2.5/weather?zip=" + zipCode
                + (!countryCode.equals("")? "," + countryCode : "" )
                + "&appid=" + appid;
        return new ObjectMapper().readValue(url, ApiResponse.class);
    }

//    public static String getInRectangle(){ //funziona con il boundingbox, non penso lo ueseremo mai
//        return = "api.openweathermap.org/data/2.5/box/city?bbox={bbox}&appid={API key}";
//        return apicall;
//    }
//    public static String getInCircle(IQuery query, String appid, IParameters params){ // non credo ci servirà mai
//        String apicall = "api.openweathermap.org/data/2.5/box/city?bbox=%s&appid=%s";
//        return apicall;
//    }
    //Getters and setters

    public String getAppid(){ return appid;}
}
