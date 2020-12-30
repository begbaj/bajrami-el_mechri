package com.umidity.database;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.umidity.api.Single;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a Humidity record, and is stored as Json object in the Database.
 */
public class HumidityRecord {

    private double humidity;
    private long timestamp;
    private CityRecord city;

    /**
     * Humidity getter
     * @return humidity percentage
     */
    public double getHumidity() { return humidity; }

    /**
     * Returns when the humidity value was recorded
     * @return the timestamp (UNIX)
     */
    public long getTimestamp() { return timestamp; }

    /**
     * Returns the City where the humidity value was recorded
     * @return CityRecord object
     */
    public CityRecord getCity() { return city; }

    /**
     * HumidityRecord constructor
     * @param humidity humidity value
     * @param timestamp timestamp when the humidity was recorded
     * @param city city where the city was recorded
     */
    @JsonCreator //when serializing/deserializing Jackson's objectMapper uses this constructor
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

    /**
     * Converts a Single object into a HumidityRecord object
     * @param single Single object to convert
     * @return converted HumidityRecord object
     */
    static public HumidityRecord singleToHumidityRecord(Single single){
        return new HumidityRecord(single.getHumidity(),
                single.getTimestamp(),
                new CityRecord(single.getCityId(), single.getCityName(), single.getCoord()));
    }

    /**
     * Converts a list of Single objects into a list of HumidityRecord objects
     * @param singles list of Single objects to convert
     * @return converted HumidityRecord list
     */
    static public List<HumidityRecord> singlesToHumidityRecord(List<Single> singles) {
        List<HumidityRecord> records = new ArrayList<>();
        for (var s : singles) {
            records.add(singleToHumidityRecord(s));
        }
        return records;
    }
}
