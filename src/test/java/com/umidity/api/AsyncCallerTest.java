package com.umidity.api;

import com.umidity.Coordinates;
import com.umidity.api.caller.ApiCaller;
import com.umidity.api.caller.AsyncCaller;
import com.umidity.api.caller.EMode;
import com.umidity.api.caller.EUnits;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class AsyncCallerTest {
    AsyncCaller caller;

    @AfterEach
    void tearDown(){
        caller = null;
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

            assertEquals("Senigallia", caller.apiResponse.elementAt(0).name);
        } catch (InterruptedException e) {
            fail();
        }
    }

    @Test
    void testByCityId(){
        caller = new AsyncCaller( new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric),
                10000, AsyncCaller.AsyncMethod.byCityId, new int[]{3166740});
        try {
            caller.start();
            Thread.sleep(500);
            caller.close();
            caller.join();

            assertFalse(caller.getRunningStatus());
            assertFalse(caller.isAlive());

            assertEquals("Senigallia", caller.apiResponse.elementAt(0).name);
        } catch (InterruptedException e) {
            fail();
        }
    }

    @Test
    void testByCoord(){
        caller = new AsyncCaller( new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric),
                10000, AsyncCaller.AsyncMethod.byCoordinates, new Coordinates(43.713056f,13.218333f));
        try {
            caller.start();
            Thread.sleep(500);
            caller.close();
            caller.join();

            assertFalse(caller.getRunningStatus());
            assertFalse(caller.isAlive());

            assertEquals("Senigallia", caller.apiResponse.elementAt(0).name);
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

            assertEquals("Roncitelli", caller.apiResponse.elementAt(0).name);
        } catch (InterruptedException e) {
            fail();
        }
    }

}