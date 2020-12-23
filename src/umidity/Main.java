package umidity;

import umidity.cli.MainCli;
import umidity.gui.*;
import umidity.database.DatabaseManager;

import java.util.Date;

//TODO: SCEGLIERE CIFRE DOPO LA VIRGOLA

public class Main {
    public static UserSettings userSettings = new UserSettings();
    public static DatabaseManager dbms =new DatabaseManager();


    public static void main(String[] args){
        Debugger.setActive(true); //TODO: da rimuovere in release
        Date time=new Date();
        userSettings.interfaceSettings.guiEnabled = false;
        if(userSettings.interfaceSettings.guiEnabled){
             MainFrame Frame=new MainFrame();
        }else{
            MainCli mainCli = new MainCli();

            //mainCli.setRefreshRate(10);
            mainCli.run();
            Debugger.println("chiuso");
        }
    }

}
