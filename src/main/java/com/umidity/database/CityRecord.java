package com.umidity.database;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.umidity.Coordinates;

import java.util.Objects;

/**
 * This class represents a City, and is stored as Json object in the Database.
 */
public class CityRecord {
    private int id;
    private String name;
    private Coordinates coord;

    /**
     * City id getter
     * @return city's id
     */
    public int getId() {
        return id;
    }

    /**
     * City name getter
     * @return city's id
     */
    public String getName() {
        return name;
    }

    /**
     * City coordinates getter
     * @return city's coordinates as a Coordinates object
     */
    public Coordinates getCoord() {
        return coord;
    }

    /**
     * CityRecord constructor
     * @param id city's id
     * @param name city's name
     * @param coord city's coordinates
     */
    @JsonCreator //when serializing/deserializing Jackson's objectMapper uses this constructor
    public CityRecord(@JsonProperty("id") int id,
                      @JsonProperty("name") String name,
                      @JsonProperty("coord") Coordinates coord) {
        this.id=id;
        this.name=name;
        this.coord=coord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityRecord that = (CityRecord) o;
        return id == that.id &&
                name.equals(that.name) &&
                coord.equals(that.coord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coord);
    }
}
