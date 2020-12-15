package umidity.statistics;

import umidity.database.HumidityRecord;

import java.util.*;


public class StatsCalculator {
    //TODO: DECIDERE COMPORTAMENTO FILTRI
    public double min(List<HumidityRecord> records, Date afterthisDate){
        HumidityRecord minimum_record =records.stream().filter(x -> x.getDate().after(afterthisDate)).min(Comparator.comparing(HumidityRecord::getHumidity)).orElseThrow(NoSuchElementException::new);
        return minimum_record.getHumidity();
    }

    public double max(List<HumidityRecord> records, Date afterthisDate){
        HumidityRecord maximum_record =records.stream().filter(x -> x.getDate().after(afterthisDate)).max(Comparator.comparing(HumidityRecord::getHumidity)).orElseThrow(NoSuchElementException::new);
        return maximum_record.getHumidity();
    }

    public double avg(List<HumidityRecord> records, Date afterthisDate){
            double avg = 0;
            if(!records.isEmpty()) {
                for (HumidityRecord record : records) {
                    if(record.getDate().after(afterthisDate))
                        avg += record.getHumidity();
                }
                return avg / records.size();
            }
            else {
                return -1; //TODO: SCEGLIERE COME GESTIRE LISTA VUOTA
            }
    }

    public double variance(List<HumidityRecord> records, Date afterthisDate) {
        double avg = avg(records, afterthisDate);
        double sum = 0;
        if (!records.isEmpty()) {
            for (HumidityRecord record : records) {
                if(record.getDate().after(afterthisDate))
                    sum = Math.pow(record.getHumidity() - avg, 2);
            }
            return sum / records.size();
        }
        else{
            return -1; //TODO: SCEGLIERE COME GESTIRE LISTA VUOTA
        }
    }
}
