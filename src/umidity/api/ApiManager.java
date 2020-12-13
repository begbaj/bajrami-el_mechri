package umidity.api;

import umidity.information.WeatherInfo;
import java.security.NoSuchAlgorithmException;


/**
 * Api call manager. This class handles every api call made to openweather returning the related object to every request
 */
public class ApiManager {
//    private ApiCalls caller;
//
//    public ApiManager(String appid) {
//        try {
//            caller = new ApiCalls(appid);
//        }catch (NoSuchAlgorithmException e){
//            e.printStackTrace();
//            //l'eccezione può essere causata per via del settaggio del contesto SSL... devo capire meglio cosa significhi
//        }
//    }
//
//    public WeatherInfo getByCity(String city, String stateCode, String countryCode){
//        //caller.getByCityName(city, stateCode, countryCode);
//        WeatherInfo humidity=new WeatherInfo();
//        return humidity;
//    }
//
//    public WeatherInfo getByCoordinates(float longitude, float latitude){
//        WeatherInfo humidity=new WeatherInfo();
//        return humidity;
//    }
//
//    public WeatherInfo getByZip(String zipcode){
//        WeatherInfo humidity=new WeatherInfo();
//        return humidity;
//    }
//
//
//    //GETTERS AND SETTERS
//
//    private String getAppid() {
//        //caller
//        return "";
//    }
}
