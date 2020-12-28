package com.umidity.api;

import com.umidity.Coordinates;
import com.umidity.api.caller.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class AsyncCallerTest {
    AsyncCaller caller;

    @AfterEach
    void tearDown(){
        try {
            Thread.sleep(1000);
            caller = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testThrowsException(){
        boolean wasThrown = false;
        try {
            caller = new AsyncCaller( new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric),
                    10000, AsyncCaller.AsyncMethod.byCityId, "wowowoowo artorias is the best");
        } catch (IllegalArgumentException e) {
            wasThrown = true;
        }
        assertTrue(wasThrown);
    }

    @Test
    void testOneCall1(){
        caller = new AsyncCaller( new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric),
                10000,AsyncCaller.AsyncMethod.oneCall1, 43.713056f, 13.218333f, EnumSet.of(EExclude.alerts));
        try {
            caller.start();
            Thread.sleep(500);
            caller.close();
            caller.join();

            assertFalse(caller.getRunningStatus());
            assertFalse(caller.isAlive());

            assertNull(caller.oneCallResponse.elementAt(0).getAlerts());
            assertNotNull(caller.oneCallResponse.elementAt(0).getAlerts());
        } catch (InterruptedException e) {
            fail();
        }
    }

    @Test
    void testOneCall2(){
        caller = new AsyncCaller( new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric),
                10000,AsyncCaller.AsyncMethod.oneCall2, 43.713056f, 13.218333f, Calendar.getInstance().getTimeInMillis()/1000);
        try {
            caller.start();
            Thread.sleep(500);
            caller.close();
            caller.join();

            assertFalse(caller.getRunningStatus());
            assertFalse(caller.isAlive());

            assertNotNull(caller.oneCallHistoricalResponse.elementAt(0).hourly);
        } catch (InterruptedException e) {
            fail();
        }
    }

    @Test
    void testByCityName(){
        caller = new AsyncCaller( new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric),
                 10000,AsyncCaller.AsyncMethod.byCityName, "Senigallia", "","");
        try {
            caller.start();
            Thread.sleep(500);
            caller.close();
            caller.join();

            assertFalse(caller.getRunningStatus());
            assertFalse(caller.isAlive());

            assertEquals("Senigallia", caller.apiResponse.elementAt(0).getCityName());
        } catch (InterruptedException e) {
            fail();
        }
    }

    @Test
    void testByCityId(){
        caller = new AsyncCaller( new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric),
                10000, AsyncCaller.AsyncMethod.byCityId, (Object) new Integer[]{3166740});
        try {
            caller.start();
            Thread.sleep(500);
            caller.close();
            caller.join();

            assertFalse(caller.getRunningStatus());
            assertFalse(caller.isAlive());

            assertEquals("Senigallia", caller.apiResponse.elementAt(0).getCityName());
        } catch (InterruptedException e) {
            fail();
        }
    }

    @Test
    void testByCoord(){
        caller = new AsyncCaller( new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric),
                10000, AsyncCaller.AsyncMethod.byCoordinates, 43.713056f,13.218333f);
        try {
            caller.start();
            Thread.sleep(500);
            caller.close();
            caller.join();

            assertFalse(caller.getRunningStatus());
            assertFalse(caller.isAlive());

            assertEquals("Senigallia", caller.apiResponse.elementAt(0).getCityName());
        } catch (InterruptedException e) {
            fail();
        }
    }

    @Test
    void testByZipCode(){
        caller = new AsyncCaller( new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric),
                10000, AsyncCaller.AsyncMethod.byZipCode, "60019", "it");
        try {
            caller.start();
            Thread.sleep(500);
            caller.close();
            caller.join();

            assertFalse(caller.getRunningStatus());
            assertFalse(caller.isAlive());

            assertEquals("Roncitelli", caller.apiResponse.elementAt(0).getCityName());
        } catch (InterruptedException e) {
            fail();
        }
    }

    @Test
    void testForecastByCityName(){
        caller = new AsyncCaller( new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric),
                10000,AsyncCaller.AsyncMethod.forecastByCityName, "Senigallia", "","");
        try {
            caller.start();
            Thread.sleep(500);
            caller.close();
            caller.join();

            assertFalse(caller.getRunningStatus());
            assertFalse(caller.isAlive());

            assertEquals("Senigallia", caller.forecastResponse.elementAt(0).getCityName());
        } catch (InterruptedException e) {
            fail();
        }
    }

    @Test
    void testForecastByCityId(){
        caller = new AsyncCaller( new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric),
                10000, AsyncCaller.AsyncMethod.forecastByCityId, (Object) new Integer[]{3166740});
        try {
            caller.start();
            Thread.sleep(500);
            caller.close();
            caller.join();

            assertFalse(caller.getRunningStatus());
            assertFalse(caller.isAlive());

            assertEquals("Senigallia", caller.forecastResponse.elementAt(0).getCityName());
        } catch (InterruptedException e) {
            fail();
        }
    }

    @Test
    void testForecastByCoord(){
        caller = new AsyncCaller( new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric),
                10000, AsyncCaller.AsyncMethod.forecastByCoordinates, 43.713056f,13.218333f);
        try {
            caller.start();
            Thread.sleep(500);
            caller.close();
            caller.join();

            assertFalse(caller.getRunningStatus());
            assertFalse(caller.isAlive());

            assertEquals("Senigallia", caller.forecastResponse.elementAt(0).getCityName());
        } catch (InterruptedException e) {
            fail();
        }
    }

    @Test
    void testForecastByZipCode(){
        caller = new AsyncCaller( new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric),
                10000, AsyncCaller.AsyncMethod.forecastByZipCode, "60019", "it");
        try {
            caller.start();
            Thread.sleep(500);
            caller.close();
            caller.join();

            assertFalse(caller.getRunningStatus());
            assertFalse(caller.isAlive());

            assertEquals("Roncitelli", caller.forecastResponse.elementAt(0).getCityName());
        } catch (InterruptedException e) {
            fail();
        }
    }

}