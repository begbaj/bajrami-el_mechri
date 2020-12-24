package com.umidity.database;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.umidity.api.response.Coordinates;

import java.util.Objects;

public class CityRecord {
    private int id;
    private String name;
    private Coordinates coord;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoord() {
        return coord;
    }

    @JsonCreator //Permette di specifiare quali attributi vengono scritti su file se l'oggetto viene salvato su file
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
