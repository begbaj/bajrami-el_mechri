package com.umidity.api;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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
        caller = new AsyncCaller( new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EMode.JSON, EUnits.Metric),
                AsyncCaller.AsyncMethod.byCityName, 1000, "Senigallia", "","");
        try {
            assertNull(caller.apiIResponse);
            caller.start();
            Thread.sleep(2000);
            assertNotNull(caller.apiIResponse);
            caller.apiIResponse = null;
            Thread.sleep(2000);
            assertNotNull(caller.apiIResponse);
            caller.close();
            caller.apiIResponse = null;
            Thread.sleep(2000);
            assertNull(caller.apiIResponse);
        } catch (InterruptedException e) {
            fail();
        }
    }

}