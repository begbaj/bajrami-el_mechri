package umidity;

import umidity.api.response.*;
import umidity.database.DatabaseManager;
import umidity.database.HumidityRecord;
import umidity.gui.MainFrame;

import java.io.IOException;
import java.util.Date;


public class Main {
    public static void main(String[] args) throws IOException {
        MainFrame Frame=new MainFrame();
        Date time=new Date();
        Coordinates location=new Coordinates();
        location.lat=3;
        location.lon=3;
        HumidityRecord record = new HumidityRecord(13, time, 4, location);
        DatabaseManager DBSM=new DatabaseManager();
        DBSM.addHumidity(record);
        DBSM.getHumidity(record.getCity_id());
    }

}
