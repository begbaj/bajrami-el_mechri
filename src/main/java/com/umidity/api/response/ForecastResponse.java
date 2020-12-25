package com.umidity.api.response;

import com.umidity.Pair;

import java.util.Vector;

public class ForecastResponse extends Response implements ICoordinates, IHumidities {
    public String cod;
    public int message;
    public int cnt;
    public ApiResponse[] list;
    public City city;

    @Override
    public Coordinates getCoord() {
        return city.coord.getCoord();
    }

    @Override
    public Vector<Pair<Long, Integer>> getHumidities() {
        Vector<Pair<Long,Integer>> humidities = new Vector<>();

        for(ApiResponse a:list){
            humidities.add(new Pair<>(Long.parseLong(a.dt), a.main.getHumidity()));
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
