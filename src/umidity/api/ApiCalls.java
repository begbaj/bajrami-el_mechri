package umidity.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import umidity.UserSettings;
import umidity.api.response.ApiResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class handles every connection to the apis.
 */
public class ApiCalls {

    /**
     * Api key used for making api calls
     */
    private final String appid;
    private EMode EMode;
    private EUnits EUnits;
    private String endParams;

    //TODO: Lista di appid, in modo da utilizzarne diverse per non rischiare di saturare la connessione
    //TODO: potrebbe essere sensato rendere la classe statica?

    /**
     * Api caller
     * @param appid Api key
     */
    public ApiCalls(String appid, EMode EMode, EUnits EUnits){
        this.appid = appid;
        this.EMode = EMode;
        this.EUnits = EUnits;
        setEndParams();
    }

    public String getAppid(){ return appid; }
    public void setMode(EMode value){ EMode = value; setEndParams(); }
    public void setUnit(EUnits value){ EUnits = value; setEndParams(); }
    public EMode getMode(){ return EMode; }
    public EUnits getUnit(){ return EUnits; }

    /**
     * automatically returns end parameters such as units, mode and appid
     */
    private void setEndParams(){
        this.endParams = "&appid=" + appid;
        if(EMode != EMode.JSON) this.endParams += "&mode=" + EMode;
        if(EUnits != EUnits.Standard) this.endParams += "&units=" + EUnits;
    }

    /**
     * Get ApiResponse by city name
     * @param cityName REQUIRED: city name
     * @param stateCode OPTIONAL: state code
     * @param countryCode OPTIONAL: country code
     */
    public ApiResponse getByCityName(String cityName, String stateCode, String countryCode)
            throws JsonProcessingException, MalformedURLException, IOException {
        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + cityName
                + (!stateCode.equals("") ? "," + stateCode : "")
                + (!countryCode.equals("") ? "," + countryCode : "")
                + endParams;

        return new ObjectMapper().readValue(new URL(url), ApiResponse.class);
    }

    /**
     * Get ApiResponse by a single city id
     * @param cityId city id
     */
    public ApiResponse getByCityId(String cityId)
            throws JsonProcessingException, MalformedURLException, IOException  {
        String url = "https://api.openweathermap.org/data/2.5/weather?id=" + cityId + endParams;
        return new ObjectMapper().readValue(new URL(url), ApiResponse.class);
    }

    /**
     * Get ApiResponse by a list of city ids
     * @param cityIds String array of city ids
     */
    public ApiResponse getByCityIds(String[] cityIds)
            throws JsonProcessingException, MalformedURLException, IOException  {
        StringBuilder url = new StringBuilder("https://api.openweathermap.org/data/2.5/weather?id=");
        boolean first = true;
        for(String s:cityIds){
            if(!first) url.append(",");
            url.append(s);
            first = false;
        }
        url.append(endParams);
        return new ObjectMapper().readValue(new URL(url.toString()), ApiResponse.class);
    }

    /**
     * Get ApiResponse by Coordinates
     * @param lat latitude
     * @param lon longitude
     */
    public ApiResponse getByCoordinates(float lat, float lon)
            throws JsonProcessingException, MalformedURLException, IOException  {
        String url =  "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + endParams;
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL(url), ApiResponse.class);
    }

    /**
     * Get ApiResponse by Zip Code
     * @param zipCode REQUIRED: zip code
     * @param countryCode OPTIONAL: let this empty if no countryCode is needed
     */
    public ApiResponse getByZipCode(String zipCode, String countryCode)
            throws JsonProcessingException, MalformedURLException, IOException  {
        String url = "https://api.openweathermap.org/data/2.5/weather?zip=" + zipCode
                + (!countryCode.equals("")? "," + countryCode : "" )
                + endParams;
        return new ObjectMapper().readValue(new URL(url), ApiResponse.class);
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
