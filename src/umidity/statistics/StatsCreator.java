package umidity.statistics;

import umidity.database.HumidityRecord;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;

public class StatsCreator {
    public double min(List<HumidityRecord> records){
        HumidityRecord minimum_record =records.stream().min(Comparator.comparing(HumidityRecord::getHumidity)).orElseThrow(NoSuchElementException::new);
        return minimum_record.getHumidity();
    }

    public double max(List<HumidityRecord> records){
        HumidityRecord maximum_record =records.stream().max(Comparator.comparing(HumidityRecord::getHumidity)).orElseThrow(NoSuchElementException::new);
        return maximum_record.getHumidity();
    }

    public double avg(List<HumidityRecord> records){
            double avg = 0;
            if(!records.isEmpty()) {
                for (HumidityRecord record : records) {
                    avg += record.getHumidity();
                }
                return avg / records.size();
            }
            else {
                return -999; //TODO: SCEGLIERE COME GESTIRE LISTA VUOTA
            }
    }

    public double variance(List<HumidityRecord> records) {
        double avg = avg(records);
        double sum = 0;
        if (!records.isEmpty()) {
            for (HumidityRecord record : records) {
                sum = Math.pow(record.getHumidity() - avg, 2);
            }
            return sum / records.size();
        }
        else{
            return -999; //TODO: SCEGLIERE COME GESTIRE LISTA VUOTA
        }
    }
}
