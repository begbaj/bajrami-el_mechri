package umidity.api;

import umidity.information.WeatherInfo;

import java.util.Map;


public class ApiManager {
    private final String appid;

    public ApiManager(String appid){
        this.appid = appid;
    }

    //TODO: file contenente le stringhe di contatto all'api in formato json

    private String getAppid() {
        return appid;
    }

    public WeatherInfo getByCity(String city){

        WeatherInfo humidity=new WeatherInfo();
        return humidity;
    }

    public WeatherInfo getByCoordinates(float longitude, float latitude){
        WeatherInfo humidity=new WeatherInfo();
        return humidity;
    }

    public WeatherInfo getByZip(String zipcode){
        WeatherInfo humidity=new WeatherInfo();
        return humidity;
    }


}
