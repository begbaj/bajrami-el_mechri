package com.umidity.api;

import com.umidity.api.caller.ApiCaller;
import com.umidity.api.caller.EMode;
import com.umidity.api.caller.EUnits;
import com.umidity.api.response.ApiResponse;
import com.umidity.api.caller.EExclude;
import com.umidity.api.response.ForecastResponse;
import com.umidity.api.response.OneCallResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class ApiCallerTest {
    private static ApiCaller caller;

    @BeforeAll
    static void initAll() {
        caller = new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric);
    }

    @BeforeEach
    void setUp(){
        try {
            Thread.sleep(1000); //Wait between calls to avoid saturation
        } catch (InterruptedException e) {
            fail();
        }
    }

    @Test
    void oneCall() {
        EnumSet<EExclude> set1,set2;
        set1 = EnumSet.of(EExclude.alerts);
        set2 = EnumSet.allOf(EExclude.class);
        assertTrue(set2.contains(EExclude.current));
        try {
            OneCallResponse r1 = caller.oneCall(43.713056f, 13.218333f, set1 );
            OneCallResponse r2 = caller.oneCall(43.713056f, 13.218333f, set2 );
            assertNotNull(r1);
            assertAll("excluded",
                    ()->assertNull(r2.alerts),
                    ()->assertNull(r2.minutely),
                    ()->assertNull(r2.hourly),
                    ()->assertNull(r2.daily),
                    ()->assertNull(r2.current)
                    );
            assertNull(r1.alerts);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testOneCall() {
        //TODO: test per oncall historical
    }

    @Test
    void getByCityName() {
        try {
            ApiResponse r1 = caller.getByCityName("Rome","","us");
            ApiResponse r2 = caller.getByCityName("Rome","","it");
            assertNotEquals(r1.getCoord().lat, r2.getCoord().lat);
            assertNotNull(r1.main);
            assertNotNull(r2.main);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void getByCityId() {
        try {
            ApiResponse r1 = caller.getByCityId("3166740");
            ApiResponse r2 = caller.getByCityId("3166711");

            assertNotNull(r1);
            assertNotNull(r2);

            assertEquals(r1.name, "Senigallia");
            assertEquals(r2.name, "Seregno");
            assertNotNull(r1.main);
            assertNotNull(r2.main);

        } catch (IOException e) {
            fail();
        }
    }


    @Test
    void getByCoordinates() {
        try {
            ApiResponse r1 = caller.getByCoordinates(43.713056f,13.218333f);
            assertNotNull(r1);

            assertEquals(r1.name, "Senigallia");
            assertNotNull(r1.main);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void getByZipCode() {
        try {
            ApiResponse r1 = caller.getByZipCode("60019", "it");
            assertNotNull(r1);
            assertEquals(r1.name, "Roncitelli");
            assertNotNull(r1.main);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void getForecastByCityName() {
        try {
            ForecastResponse r1 = caller.getForecastByCityName("Rome","","us");
            ForecastResponse r2 = caller.getForecastByCityName("Rome","","it");
            assertNotEquals(r1.getCoord(), r2.getCoord());
            assertTrue(r1.getHumidities().size()>0);
            assertTrue(r2.getHumidities().size() > 0);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void getForecastByCityId() {
        try {
            ForecastResponse r1 = caller.getForecastByCityId("3166740");
            ForecastResponse r2 = caller.getForecastByCityId("3166711");

            assertNotNull(r1);
            assertNotNull(r2);

            assertEquals(r1.city.name, "Senigallia");
            assertTrue(r1.list.length > 0);
            assertEquals(r2.city.name, "Seregno");
            assertTrue(r2.list.length > 0);

        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void getForecastByCoordinates() {
        try {
            ForecastResponse r1 = caller.getForecastByCoordinates(43.713056f,13.218333f);
            assertNotNull(r1);

            assertEquals(r1.city.name, "Senigallia");
            assertTrue(r1.list.length > 0);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void getForecastByZipCode() {
        try {
            ForecastResponse r1 = caller.getForecastByZipCode("60019", "it");
            assertNotNull(r1);
            assertEquals(r1.city.name, "Roncitelli");
            assertTrue(r1.list.length > 0);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void getForecastSingles(){
        try {
            ForecastResponse r1 = caller.getForecastByCityId("3166740");
            assertNotNull(r1.getCoord());
            assertNotNull(r1.getSingle().getCoord());
            assertNotNull(r1.getSingles()[1].getCoord());
        } catch (IOException e) {
            fail();
        }

    }
}