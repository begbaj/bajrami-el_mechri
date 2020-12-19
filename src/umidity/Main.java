package umidity;

import umidity.cli.*;
import umidity.gui.*;
import umidity.database.DatabaseManager;

import java.util.Date;

//TODO: SCEGLIERE CIFRE DOPO LA VIRGOLA

public class Main {
    public static UserSettings userSettings;
    protected DatabaseManager dbms =new DatabaseManager();


    public static void main(String[] args){
        Debugger.setActive(true); //TODO: da rimuovere in release
        Date time=new Date();

        if(userSettings.interfaceSettings.guiEnabled){
            MainFrame Frame=new MainFrame();
        }else{
            MainScreen main = new MainScreen();
        }

//testing
//        Coordinates location=new Coordinates();
//        location.lat=3;
//        location.lon=3;
//        HumidityRecord record = new HumidityRecord(2, time, 78, location);
//        dbms.addHumidity(record);
//        StatsCalculator statsCalculator =new StatsCalculator();
//        Debugger.println(String.valueOf(statsCalculator.min(dbms.getHumidity(record.getCity_id()), time)));
//        Debugger.println(String.valueOf(statsCalculator.max(dbms.getHumidity(record.getCity_id()), time)));
//        Debugger.println(String.valueOf(statsCalculator.avg(dbms.getHumidity(record.getCity_id()), time)));
//        Debugger.println(String.valueOf(statsCalculator.variance(dbms.getHumidity(record.getCity_id()), time)));
    }

}
