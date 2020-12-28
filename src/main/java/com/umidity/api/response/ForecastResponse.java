package com.umidity.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.umidity.Coordinates;
import com.umidity.ICoordinates;
import com.umidity.IHumidities;
import com.umidity.Pair;
import com.umidity.api.Single;

import java.util.Map;
import java.util.Vector;

//TODO: documentazione
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastResponse extends Response implements ICoordinates, IHumidities {

    @JsonProperty("cnt")
    public int count;
    public ApiResponse[] list;

    @Override
    public Vector<Pair<Long, Integer>> getHumidities() {
        Vector<Pair<Long,Integer>> humidity = new Vector<>();

        for(ApiResponse a:list){
            humidity.add(new Pair<>(a.getTimestamp(), a.getHumidity()));
        }

        return humidity;
    }

    @JsonProperty("city")
    public void unpackCity(Map<String, Object> map){
        if(map.get("id") != null) this.cityId = Integer.parseInt(String.valueOf(map.get("id")));
        if(map.get("name") != null) this.cityName = String.valueOf(map.get("name"));
        if(map.get("country") != null) this.cityCountry = String.valueOf(map.get("country"));
        if(map.get("country") != null) this.cityCountry = String.valueOf(map.get("country"));
        if(map.get("sunrise") != null) this.sunrise = Long.parseLong(String.valueOf(map.get("sunrise")));
        if(map.get("sunset") != null) this.sunset = Long.parseLong(String.valueOf(map.get("sunset")));
        this.coord = new Coordinates(
                Float.parseFloat(String.valueOf(((Map<String, Object>)map.get("coord")).get("lat"))),
                Float.parseFloat(String.valueOf(((Map<String, Object>)map.get("coord")).get("lat"))));

    }
    /**
     * Get first forecast on the list aa a Single object.
     * @return
     */
    @Override
    public Single getSingle(){
        if(list.length > 0){
            list[0].setCityId(cityId);
            list[0].setCoord(coord);
            list[0].setCityName(cityName);
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
            a.setCityId(cityId);
            a.setCoord(coord);
            a.setCityName(cityName);
        }
        if(list.length > 0)
            return Single.getSingles(list);
        return null;
    }

}
