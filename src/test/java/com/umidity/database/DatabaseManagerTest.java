package com.umidity.database;

import com.umidity.Coordinates;
import com.umidity.UserSettings;
import com.umidity.api.caller.EUnits;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatabaseManagerTest {
    static DatabaseManager dbms;
    static UserSettings settings;
    @BeforeAll
    static void initAll(){
        dbms = new DatabaseManager("test/records/");
        settings = new UserSettings();

        settings.setUnits(EUnits.Metric);
        settings.setGuiEnabled(true);
        settings.setGuiTheme("default");
        settings.setCliPrompt(">");
        dbms.recreate();

    }

    @AfterEach
    void tearDown(){
        dbms.recreate();
    }


    @Test
    void notEqualCityInsertion(){
        var record1 = new CityRecord(3166740,"Senigallia", new Coordinates(13.21667f, 43.709259f));
        var record2 = new CityRecord(3166740,"Senigallia", new Coordinates(13.21667f, 43.709259f));

        dbms.addCity(record1);
        dbms.addCity(record2);

        assertEquals(1, dbms.getCities().size());
    }

    @Test
    void notEqualHumidityInsertion(){
        var cityRecord1 = new CityRecord(3166740,"Senigallia", new Coordinates(13.21667f, 43.709259f));
        var cityRecord2 = new CityRecord(3183089,"Ancona", new Coordinates(13.51008f, 43.59816f));
        var timestamp= Calendar.getInstance().getTimeInMillis();
        var humidity11 = new HumidityRecord(50, timestamp, cityRecord1 );
        var humidity12 = new HumidityRecord(45, timestamp, cityRecord1 );

        var humidity21 = new HumidityRecord(50, timestamp, cityRecord2 );
        var humidity22 = new HumidityRecord(65, timestamp, cityRecord2 );

        dbms.addHumidity(humidity11);
        dbms.addHumidity(humidity12);

        dbms.addHumidity(humidity21);
        dbms.addHumidity(humidity22);

        assertEquals(1, dbms.getHumidity(cityRecord1.getId()).size());
        assertEquals(1, dbms.getHumidity(cityRecord2.getId()).size());
    }
}