package umidity;

import umidity.database.DatabaseManager;
import umidity.gui.MainFrame;
import umidity.information.PlaceInfo;
import umidity.information.WeatherInfo;

import java.text.SimpleDateFormat;


public class Main {
    public static void main(String[] args) {
        MainFrame Frame=new MainFrame();
        SimpleDateFormat today = new SimpleDateFormat(); //Always pass in a time zone
        PlaceInfo location=new PlaceInfo("Ancona", "Italy", "60120", 123313, 13311);
        WeatherInfo record = new WeatherInfo(13, today, location);
        System.out.println(record);
        DatabaseManager DBSM=new DatabaseManager();
        DBSM.addHumidity(record);
    }

}
