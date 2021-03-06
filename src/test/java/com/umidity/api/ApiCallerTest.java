package com.umidity.api;

import com.umidity.api.caller.ApiCaller;
import com.umidity.api.caller.EMode;
import com.umidity.api.caller.EUnits;
import com.umidity.api.response.ApiResponse;
import com.umidity.api.caller.EExclude;
import com.umidity.api.response.ForecastResponse;
import com.umidity.api.response.OneCallHistoricalResponse;
import com.umidity.api.response.OneCallResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
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
    void oneCall1() {
        EnumSet<EExclude> set1,set2;
        set1 = EnumSet.of(EExclude.alerts);
        set2 = EnumSet.allOf(EExclude.class);
        assertTrue(set2.contains(EExclude.current));
        try {
            OneCallResponse r1 = caller.oneCall(43.713056f, 13.218333f, set1 );
            OneCallResponse r2 = caller.oneCall(43.713056f, 13.218333f, set2 );
            assertNotNull(r1);
            assertAll("excluded",
                    ()->assertNull(r2.getAlerts()),
                    ()->assertNull(r2.getMinutely()),
                    ()->assertNull(r2.getHourly()),
                    ()->assertNull(r2.getDaily())
                    );
            assertEquals(0,r2.getTemp());
            assertNull(r1.getAlerts());
            assertNotEquals(0, r1.getHumidity());
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void OneCall2() {
        try {
            OneCallHistoricalResponse r1 = caller.oneCall(43.713056f, 13.218333f, Calendar.getInstance().getTimeInMillis()/1000);
            assertNotNull(r1.hourly);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getByCityName() {
        try {
            ApiResponse r1 = caller.getByCityName("Rome","","us");
            ApiResponse r2 = caller.getByCityName("Rome","","it");
            assertNotEquals(r1.getCoord().lat, r2.getCoord().lat);
            assertEquals("Rome", r1.getCityName());
            assertEquals("Rome",r2.getCityName());
            assertNotEquals(r1.getCoord(), r2.getCoord());
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

            assertEquals(r1.getCityName(), "Senigallia");
            assertEquals(r2.getCityName(), "Seregno");
            assertNotNull(r1.getHumidity());
            assertNotNull(r2.getHumidity());

        } catch (IOException e) {
            fail();
        }
    }


    @Test
    void getByCoordinates() {
        try {
            ApiResponse r1 = caller.getByCoordinates(43.713056f,13.218333f);
            assertNotNull(r1);

            assertEquals(r1.getCityName(), "Senigallia");
            assertNotNull(r1.getHumidity());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void getByZipCode() {
        try {
            ApiResponse r1 = caller.getByZipCode("60019", "it");
            assertNotNull(r1);
            assertEquals(r1.getCityName(), "Roncitelli");
            assertNotNull(r1.getHumidity());
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

            assertEquals(r1.getCityName(), "Senigallia");
            assertTrue(r1.list.length > 0);
            assertEquals(r2.getCityName(), "Seregno");
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

            assertEquals(r1.getCityName(), "Senigallia");
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
            assertEquals(r1.getCityName(), "Roncitelli");
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