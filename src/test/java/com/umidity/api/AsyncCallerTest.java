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

}