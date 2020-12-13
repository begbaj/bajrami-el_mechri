package umidity.information;

import java.util.Vector;

@Deprecated
public class LoadedInformation implements Information{
    WeatherInfo currentWeather;
    Vector<WeatherInfo> forecastWeather;

    public void loadDatabase(){}

    public void saveDatabase(){}
}
