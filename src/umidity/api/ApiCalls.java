package umidity.api;

/**
 * This class returns formatted API calls for later use
 */
public class ApiCalls {
//questa classe contiene il necessario per effettuare le chiamate alle api di OpenWeather

    public static String appid;

    public static String getByCityName(String cityName, String stateCode, String countryCode){
        return "api.openweathermap.org/data/2.5/weather?q="
                + cityName
                + (!stateCode.equals("") ? "," + stateCode : "")
                + (!countryCode.equals("") ? "," + countryCode : "")
                + "&appid=" + appid;
    }

    public static String getByCityId(String cityId){
        return "api.openweathermap.org/data/2.5/weather?id=" + cityId + "&appid=" + appid;
    }
    public static String getByCityIds(String[] cityIds){
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
    public static String getByCoordinates(String lat, String lon){
        return "api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + appid;
    }
    public static String getByZipCode(String zipCode, String countryCode){
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

}
