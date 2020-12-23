package com.umidity.api.response;

import java.util.HashMap;
import java.util.Map;

public class ForecastIResponse extends Response implements IResponse, ICoordinates, IHumidities {
    public String cod;
    public int message;
    public int cnt;
    public ApiIResponse[] list;
    public City city;

    @Override
    public Coordinates getCoord() {
        return city.coord.getCoord();
    }

    @Override
    public Map<Long, Integer> getHumidities() {
        Map<Long,Integer> humidities = new HashMap<>();
        for(ApiIResponse a:list){
            humidities.put(Long.parseLong(a.dt), a.main.getHumidity());
        }
        return humidities;
    }

    public class City{
        public long id;
        public String name;
        public Coordinates coord;
        public String country;
        public long population;
        public long timezone;
        public long sunrise;
        public long sunset;
    }

}
