package umidity.api;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import com.fasterxml.jackson.databind.*;

/**
 * This class handles every connection to the apis.
 */
public class ApiCalls {
//questa classe contiene il necessario per effettuare le chiamate alle api di OpenWeather

    /**
     * Api key used for making api calls
     */
    private String appid;

    public ApiCalls(String appid) throws NoSuchAlgorithmException {

    }

     public String getByCityName(String cityName, String stateCode, String countryCode) throws URISyntaxException {
        String url = "api.openweathermap.org/data/2.5/weather?q="
                + cityName
                + (!stateCode.equals("") ? "," + stateCode : "")
                + (!countryCode.equals("") ? "," + countryCode : "")
                + "&appid=" + appid;

        ObjectMapper mapper = new ObjectMapper();
        mapper.readValue(url, );


        return "";
    }
    public String getByCityId(String cityId){
        return "api.openweathermap.org/data/2.5/weather?id=" + cityId + "&appid=" + appid;
    }
    public String getByCityIds(String[] cityIds){
        String apicall = "api.openweathermap.org/data/2.5/weather?id=";
        boolean first = true;
        for(String s:cityIds){
            if(!first) apicall += ",";
            apicall += s;
            first = false;
        }
        apicall += "&appid=" + appid;
        return apicall;
    }
    public String getByCoordinates(String lat, String lon){
        return "api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + appid;
    }
    public String getByZipCode(String zipCode, String countryCode){
        return "api.openweathermap.org/data/2.5/weather?zip=" + zipCode
                + (!countryCode.equals("")? "," + countryCode : "" )
                + "&appid=" + appid;
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
