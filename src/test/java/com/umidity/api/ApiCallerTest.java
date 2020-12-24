package com.umidity.api;

import com.umidity.api.response.ApiIResponse;
import com.umidity.api.response.EExclude;
import com.umidity.api.response.ForecastIResponse;
import com.umidity.api.response.OneCallIResponse;
import org.junit.jupiter.api.AfterEach;
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
        caller = new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EMode.JSON, EUnits.Metric);
    }

    @Test
    void oneCall() {
        EnumSet<EExclude> set1,set2;
        set1 = EnumSet.of(EExclude.alerts);
        set2 = EnumSet.allOf(EExclude.class);
        try {
            OneCallIResponse r1 = caller.oneCall(43.713056f, 13.218333f, set1 );
            OneCallIResponse r2 = caller.oneCall(43.713056f, 13.218333f, set2 );
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
            ApiIResponse r1 = caller.getByCityName("Rome","","US");
            ApiIResponse r2 = caller.getByCityName("Rome","","IT");
            assertNotEquals(r1.getCoord(), r2.getCoord());
            assertNotNull(r1.main);
            assertNotNull(r2.main);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void getByCityId() {
        try {
            ApiIResponse r1 = caller.getByCityId("3166740");
            ApiIResponse r2 = caller.getByCityId("3166711");

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
            ApiIResponse r1 = caller.getByCoordinates(43.713056f,13.218333f);
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
            ApiIResponse r1 = caller.getByZipCode("60019", "it");
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
            ForecastIResponse r1 = caller.getForecastByCityName("Rome","","us");
            ForecastIResponse r2 = caller.getForecastByCityName("Rome","","it");
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
            ForecastIResponse r1 = caller.getForecastByCityId("3166740");
            ForecastIResponse r2 = caller.getForecastByCityId("3166711");

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
            ForecastIResponse r1 = caller.getForecastByCoordinates(43.713056f,13.218333f);
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
            ForecastIResponse r1 = caller.getForecastByZipCode("60019", "it");
            assertNotNull(r1);
            assertEquals(r1.city.name, "Roncitelli");
            assertTrue(r1.list.length > 0);
        } catch (IOException e) {
            fail();
        }
    }
}