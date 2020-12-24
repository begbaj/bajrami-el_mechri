package com.umidity.database;

import com.umidity.UserSettings;
import com.umidity.api.EUnits;
import com.umidity.api.response.Coordinates;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerTest {
    static DatabaseManager dbms;
    static UserSettings settings;
    @BeforeAll
    static void initAll(){
        dbms = new DatabaseManager("test/records/");
        settings = new UserSettings();

        settings.apiSettings.units = EUnits.Metric;
        settings.interfaceSettings.guiEnabled = true;
        settings.interfaceSettings.cliUserTheme = "default";
        settings.interfaceSettings.guiUserTheme = "default";
        settings.interfaceSettings.prompt = ">";
        settings.username = "test";
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
        var date = new Date();
        var humidity11 = new HumidityRecord(50, date, cityRecord1 );
        var humidity12 = new HumidityRecord(45, date, cityRecord1 );

        var humidity21 = new HumidityRecord(50, date, cityRecord2 );
        var humidity22 = new HumidityRecord(65, date, cityRecord2 );

        dbms.addHumidity(humidity11);
        dbms.addHumidity(humidity12);

        dbms.addHumidity(humidity21);
        dbms.addHumidity(humidity22);

        assertEquals(1, dbms.getHumidity(cityRecord1.getId()).size());
        assertEquals(1, dbms.getHumidity(cityRecord2.getId()).size());

    }

    @Test
    void test() {
        dbms.setUserSettings();
        dbms.addCity(new CityRecord(3166740,"Senigallia", new Coordinates(13.21667f, 43.709259f)));
        dbms.addCity(new CityRecord(3183089,"Ancona", new Coordinates(13.51008f, 43.59816f)));
        dbms.addCity(new CityRecord(3169070,"Roma", new Coordinates(12.4839f, 41.894741f)));

        //TODO: test

    }

    @Test
    void test1(){

    }
}