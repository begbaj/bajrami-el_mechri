package com.umidity;

import com.umidity.cli.MainCli;
import com.umidity.gui.*;
import com.umidity.database.DatabaseManager;

import java.util.Date;


public class Main {
    public static UserSettings userSettings = new UserSettings();
    public static DatabaseManager dbms =new DatabaseManager();
    //TODO: unit tests [quasi]
    //TODO: arrotondamento delle cifre decimali nella gui [non lo so]

    //TODO: JavaDocs [da fare]
    //TODO: README.md [da fare]


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
