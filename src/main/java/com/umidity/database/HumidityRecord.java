package com.umidity.database;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.umidity.api.Single;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    static public HumidityRecord singleToHumidityRecord(Single single){
        return new HumidityRecord(single.getHumidity(),
                single.getTimestamp(),
                new CityRecord(single.getCityId(), single.getCityName(), single.getCoord()));
    }

    static public List<HumidityRecord> singlesToHumidityRecord(List<Single> singles) {
        List<HumidityRecord> records = new ArrayList<>();
        for (var s : singles) {
            records.add(singleToHumidityRecord(s));
        }
        return records;
    }
}
