package com.umidity.database;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Inserire documentazione
 */
public class HumidityRecord {

    private double humidity;
    private Date date;
    private CityRecord city;

    //TODO: Documentazione


    public double getHumidity() { return humidity; }
    public Date getDate() { return date; }
    public CityRecord getCity() { return city; }

    @JsonCreator //Permette di specifiare quali attributi vengono scritti su file se l'oggetto viene salvato su file
    public HumidityRecord(@JsonProperty("humidity") double humidity,
                          @JsonProperty("date") Date date,
                          @JsonProperty("city") CityRecord city){
        this.humidity = humidity;
        this.date = date;
        this.city = city;
    }

    @Override
    public String toString(){
        return String.valueOf(humidity);
    }
}
