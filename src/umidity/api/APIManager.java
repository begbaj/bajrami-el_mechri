package umidity.api;

import umidity.information.WeatherInfo;

public class APIManager {
    String appid;
    String apiURL;

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
