package com.umidity.database;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.umidity.api.response.Coordinates;

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
}
