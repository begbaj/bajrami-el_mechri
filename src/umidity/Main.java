package umidity;

import umidity.api.response.*;
import umidity.database.DatabaseManager;
import umidity.database.HumidityRecord;
import umidity.gui.MainFrame;
import umidity.statistics.StatsCreator;

import java.io.IOException;
import java.util.Date;

//TODO: SCEGLIERE CIFRE DOPO LA VIRGOLA

public class Main {
    public static void main(String[] args){
        MainFrame Frame=new MainFrame();
        Date time=new Date();
        Coordinates location=new Coordinates();
        location.lat=3;
        location.lon=3;
        HumidityRecord record = new HumidityRecord(2, time, 78, location);
        DatabaseManager DBSM=new DatabaseManager();
        DBSM.addHumidity(record);
        StatsCreator statsCreator=new StatsCreator();
        System.out.println(statsCreator.min(DBSM.getHumidity(record.getCity_id()), time));
        System.out.println(statsCreator.max(DBSM.getHumidity(record.getCity_id()), time));
        System.out.println(statsCreator.avg(DBSM.getHumidity(record.getCity_id()), time));
        System.out.println(statsCreator.variance(DBSM.getHumidity(record.getCity_id()), time));
    }

}
