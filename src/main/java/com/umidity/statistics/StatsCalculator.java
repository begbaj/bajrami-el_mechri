package com.umidity.statistics;

import com.umidity.database.HumidityRecord;

import java.util.*;

/**
 * Calculate Humidity statistics such as Minimum, Maximum, Average and Variance.
 */
public class StatsCalculator {

    /**
     * Get HumidityRecord with the lowest humidity between from and to
     * @param records List of records to scan
     * @param from from this date
     * @param to to this date
     * @return HumidityRecord with the lowest humidity recorded
     */
    public static HumidityRecord min(List<HumidityRecord> records, Date from, Date to){
        //TODO: e se ce ne fossero due o più con lo stesso valore????
        HumidityRecord min = null;
        try{
            min = records.stream()
                    .filter(x -> x.getDate().after(from))
                    .filter(x -> x.getDate().before(to))
                    .min(Comparator.comparing(HumidityRecord::getHumidity))
                    .orElseThrow(NoSuchElementException::new);
        }catch (NoSuchElementException e){
            //nulla da fare, semplicemente ritorno null per indicare che non è stato trovato nulla nel range indicato
            return null;
        }

        return min;
    }
    /**
     * Get HumidityRecord with the lowest humidity after/before this date
     * @param records List of records to scan
     * @param date from/before this date
     * @param inverse true: before, false: after
     * @return HumidityRecord with the lowest humidity recorded
     */
    public static HumidityRecord min(List<HumidityRecord> records, Date date, boolean inverse){
        HumidityRecord min = null;

        try{
            if(inverse)
                min =   records.stream()
                        .filter(x -> x.getDate().before(date))
                        .min(Comparator.comparing(HumidityRecord::getHumidity))
                        .orElseThrow(NoSuchElementException::new);
            else
                min =   records.stream()
                        .filter(x -> x.getDate().after(date))
                        .min(Comparator.comparing(HumidityRecord::getHumidity))
                        .orElseThrow(NoSuchElementException::new);
        }catch (NoSuchElementException e){
            return null;
        }

        return min;
    }

    /**
     * Get HumidityRecord with the highest humidity after/before this date
     * @param records List of records to scan
     * @param date from/before this date
     * @param inverse true: before, false: after
     * @return HumidityRecord with the highest humidity recorded
     */
    public static HumidityRecord max(List<HumidityRecord> records, Date date, boolean inverse){

        HumidityRecord max = null;
        try{
            if(inverse)
                max =   records.stream()
                        .filter(x -> x.getDate().before(date))
                        .max(Comparator.comparing(HumidityRecord::getHumidity))
                        .orElseThrow(NoSuchElementException::new);
            else
                max =   records.stream()
                        .filter(x -> x.getDate().after(date))
                        .max(Comparator.comparing(HumidityRecord::getHumidity))
                        .orElseThrow(NoSuchElementException::new);
        }catch (NoSuchElementException e){ return null; }

        return max;
    }
    /**
     * Get HumidityRecord with the highest humidity between "from" and "to"
     * @param records List of records to scan
     * @param from from this date
     * @param to to this date
     * @return HumidityRecord with the highest humidity recorded
     */
    public static HumidityRecord max(List<HumidityRecord> records, Date from, Date to){

        HumidityRecord max = null;
        try{
            max = records.stream()
                    .filter(x -> x.getDate().after(from))
                    .filter(x -> x.getDate().before(to))
                    .max(Comparator.comparing(HumidityRecord::getHumidity))
                    .orElseThrow(NoSuchElementException::new);
        }catch (NoSuchElementException e){ return null; }

        return max;
    }

    /**
     * Get average humidity after/before "date"
     * @param records List of records to scan
     * @param date from/before this date
     * @param inverse true: before, false: after
     * @return HumidityRecord with the highest humidity recorded
     */
    public static double avg(List<HumidityRecord> records, Date date, boolean inverse){

            double avg = 0;
            int count = 0;
            if(!records.isEmpty()) {
                for (HumidityRecord record : records) {
                    if(!inverse){
                        if(record.getDate().after(date)){
                            avg += record.getHumidity();
                            count++;
                        }
                    }else{
                        if(record.getDate().before(date)){
                            avg += record.getHumidity();
                            count++;
                        }
                    }
                }
                return avg / count;
            }
            else {
                return -1;
            }
    }
    /**
     * Get average humidity between "from" and "to"
     * @param records List of records to scan
     * @param from from this date
     * @param to to this date
     * @return HumidityRecord with the highest humidity recorded
     */
    public static double avg(List<HumidityRecord> records, Date from, Date to){
        double avg = 0;
        double count = 0;
        if(!records.isEmpty()) {
            for (HumidityRecord record : records) {
                    if(record.getDate().after(from) && record.getDate().before(to)){
                        avg += record.getHumidity();
                        count++;
                    }
            }
            return avg / count;
        }
        else {
            return -1;
        }
    }

    /**
     * Get variance of humidity between after/before date
     * @param records List of records to scan
     * @param date from/before this date
     * @param inverse true: before, false: after
     * @return HumidityRecord with the highest humidity recorded
     */
    public static double variance(List<HumidityRecord> records, Date date, boolean inverse) {
//        double avg = avg(records, afterthisDate);
//        double sum = 0;
//        if (!records.isEmpty()) {
//            for (HumidityRecord record : records) {
//                if(record.getDate().after(afterthisDate))
//                    sum = Math.pow(record.getHumidity() - avg, 2);
//            }
//            return sum / records.size();
//        }
//        else{
//            return -1;
//        }

        double avg = avg(records, date, inverse);
        double sum = 0;
        if (!records.isEmpty()) {
            for (HumidityRecord record : records) {
                if(inverse)
                    if(record.getDate().before(date))
                        sum = Math.pow(record.getHumidity() - avg, 2);
                else
                    if(record.getDate().after(date))
                        sum = Math.pow(record.getHumidity() - avg, 2);
            }
            return sum / records.size();
        }
        else{
            return -1;
        }
    }
    /**
     * Get variance of humidity between "from" and "to"
     * @param records List of records to scan
     * @param from from this date
     * @param to to this date
     * @return HumidityRecord with the highest humidity recorded
     */
    public static double variance(List<HumidityRecord> records, Date from, Date to) {
        double avg = avg(records, from, to);
        double sum = 0;
        if (!records.isEmpty()) {
            for (HumidityRecord record : records) {
                    if(record.getDate().after(from) && record.getDate().before(to)  )
                        sum = Math.pow(record.getHumidity() - avg, 2);
            }
            return sum / records.size();
        }
        else{
            return -1;
        }
    }
}
