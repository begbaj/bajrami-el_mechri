package com.umidity.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.umidity.Coordinates;
import com.umidity.ICoordinates;
import com.umidity.IHumidities;
import com.umidity.Pair;
import com.umidity.api.Single;

import java.util.Vector;

//TODO: documentazione
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastResponse implements Response, ICoordinates, IHumidities {
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
        public int id;
        public String name;
        public Coordinates coord;
        public String country;
        public long population;
        public long timezone;
        public long sunrise;
        public long sunset;
    }

    /**
     * Get first forecast on the list aa a Single object.
     * @return
     */
    @Override
    public Single getSingle(){
        if(list.length > 0){
            list[0].id = city.id;
            list[0].coord = city.coord;
            list[0].name = city.name;
            return new Single(list[0]);
        }
        return null;
    }

    /**
     * Get forecast list aa a Single array.
     * @return
     */
    @Override
    public Single[] getSingles(){
        for(ApiResponse a:list){
            a.id = city.id;
            a.coord = city.coord;
            a.name = city.name;
        }
        if(list.length > 0)
            return Single.getSingles(list);
        return null;
    }

}
