package com.umidity.statistics;

import com.umidity.Coordinates;
import com.umidity.database.CityRecord;
import com.umidity.database.HumidityRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StatsCalculatorTest {
    private static List<HumidityRecord> records;

    @BeforeAll
    static void initAll(){
        CityRecord city1 = new CityRecord(3166740,"Senigallia", new Coordinates(13.21667f, 43.709259f));;
        Calendar cal = Calendar.getInstance();

        records = new ArrayList<>();

        cal.add(Calendar.DATE, -1);
        records.add(new HumidityRecord(10, cal.getTimeInMillis()/1000, city1));
        cal.add(Calendar.DATE, -1);
        records.add(new HumidityRecord(15, cal.getTimeInMillis()/1000, city1));
        cal.add(Calendar.DATE, -1);
        records.add(new HumidityRecord(20, cal.getTimeInMillis()/1000, city1));
        cal.add(Calendar.DATE, -1);
        records.add(new HumidityRecord(5, cal.getTimeInMillis()/1000, city1));
        cal.add(Calendar.DATE, -1);
        records.add(new HumidityRecord(60, cal.getTimeInMillis()/1000, city1));
        cal.add(Calendar.DATE, -1);
        records.add(new HumidityRecord(40, cal.getTimeInMillis()/1000, city1));
        cal.add(Calendar.DATE, -1);
        records.add(new HumidityRecord(15, cal.getTimeInMillis()/1000, city1));
        cal.add(Calendar.DATE, -1);
        records.add(new HumidityRecord(90, cal.getTimeInMillis()/1000, city1));
    }

    @Test
    void testMin1() {
        Calendar cal = Calendar.getInstance();
        assertEquals(5.0, StatsCalculator.min(records, cal.getTime(), true).getHumidity());
        cal.add(Calendar.DATE, -4);
        assertEquals(10.0, StatsCalculator.min(records, cal.getTime(), false).getHumidity());
    }

    @Test
    void testMin2() {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.add(Calendar.DATE, -8);
        cal2.add(Calendar.DATE, -5);
        assertEquals(15.0, StatsCalculator.min(records, cal1.getTime(), cal2.getTime()).getHumidity());
    }

    @Test
    void testMax1() {
        assertEquals(90.0, StatsCalculator.max(records, new Date(), true).getHumidity());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -4);
        assertEquals(20.0, StatsCalculator.max(records, cal.getTime(), false).getHumidity());
    }

    @Test
    void testMax2() {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.add(Calendar.DATE, -7);
        cal2.add(Calendar.DATE, -2);
        assertEquals(60.0, StatsCalculator.max(records, cal1.getTime(), cal2.getTime()).getHumidity());
    }

    @Test
    void testAvg1() {
        Calendar cal = Calendar.getInstance();
        assertEquals(31.875, StatsCalculator.avg(records, cal.getTime(), true));
        cal.add(Calendar.DATE, -5);
        assertEquals(12.5, StatsCalculator.avg(records, cal.getTime(), false));
    }

    @Test
    void testAvg2() {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.add(Calendar.DATE, -3);
        cal2.add(Calendar.DATE, -7);
        assertEquals(31.25, StatsCalculator.avg(records, cal2.getTime(), cal1.getTime()));
    }

    @Test
    void testVariance1() {
        assertEquals(768.35, StatsCalculator.variance(records, new Date(), true));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -4);
        assertEquals(31.25, StatsCalculator.variance(records, cal.getTime(), false));
    }

    @Test
    void testVariance2() {

    }
}