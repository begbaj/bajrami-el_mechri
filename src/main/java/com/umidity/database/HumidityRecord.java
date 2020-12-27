package com.umidity.database;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;

/**
 * Inserire documentazione
 */
public class HumidityRecord {

    private double humidity;
    private long timestamp;
    private CityRecord city;

    //TODO: Documentazione


    public double getHumidity() { return humidity; }

    public long getTimestamp() { return timestamp; }
    public CityRecord getCity() { return city; }

    @JsonCreator //Permette di specifiare quali attributi vengono scritti su file se l'oggetto viene salvato su file
    public HumidityRecord(@JsonProperty("humidity") double humidity,
                          @JsonProperty("timestamp") long timestamp,
                          @JsonProperty("city") CityRecord city){
        this.humidity = humidity;
        this.timestamp = timestamp;
        this.city = city;
    }

    @Override
    public String toString(){
        return String.valueOf(humidity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HumidityRecord that = (HumidityRecord) o;
        return timestamp == that.timestamp && city.equals(that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, city);
    }
}
