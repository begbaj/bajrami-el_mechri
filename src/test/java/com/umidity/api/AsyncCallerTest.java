package com.umidity.api;

import com.umidity.api.caller.ApiCaller;
import com.umidity.api.caller.AsyncCaller;
import com.umidity.api.caller.EMode;
import com.umidity.api.caller.EUnits;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsyncCallerTest {
    AsyncCaller caller;

    @AfterEach
    void tearDown(){
        caller = null;
    }

    @Test
    void testByCityName(){
        caller = new AsyncCaller( new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric),
                 1000,AsyncCaller.AsyncMethod.byCityName, "Senigallia", "","");
        try {
            assertNull(caller.apiResponse);
            caller.start();
            Thread.sleep(2000);
            assertEquals("Senigallia", caller.apiResponse.elementAt(0).name);
            caller.close();
            Thread.sleep(500);
            assertFalse(caller.getRunningStatus());
        } catch (InterruptedException e) {
            fail();
        }
    }

    @Test
    void testByCityId(){
        caller = new AsyncCaller( new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric),
                 1000, AsyncCaller.AsyncMethod.byCityName, new int[]{});
        try {
            assertNull(caller.apiResponse);
            caller.start();
            Thread.sleep(2000);
            assertEquals("Senigallia", caller.apiResponse.elementAt(0).name);
            caller.close();
            Thread.sleep(500);
            assertFalse(caller.getRunningStatus());
        } catch (InterruptedException e) {
            fail();
        }
    }

}