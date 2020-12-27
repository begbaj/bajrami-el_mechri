package com.umidity;

import com.umidity.api.caller.*;
import com.umidity.cli.MainCli;
import com.umidity.gui.*;
import com.umidity.database.DatabaseManager;

import java.util.Date;


public class Main{
    public static UserSettings userSettings = new UserSettings();
    public static DatabaseManager dbms =new DatabaseManager();
    public static ApiCaller caller;
    //TODO: unit tests [quasi]
    //TODO: arrotondamento delle cifre decimali nella gui [non lo so]
    //TODO: JavaDocs [da fare]
    //TODO: README.md [da fare]

    //TODO: apicaller comune [fatto?]
    //TODO: api key nascosta [fatto? eccezioni?]
    //TODO: Main asyncaller [chiedi beg]
    //TODO: Maingui eventi asyncaller
    //TODO: Maingui Utilizza Single
    //TODO: Javadoc
    //TODO: Depreca cose inutili
    //TODO: Gestisci eccezioni grafici
    //TODO: Tema grafici

    //TODO: PUSH(Fixed settings loading, fiex jdatepicker bug,

    public static void main(String[] args){
        Debugger.setActive(true); //TODO: da rimuovere in release
        Date time=new Date();
        dbms.loadUserSettings();
        caller=new ApiCaller(userSettings.apiSettings.apikey, EMode.JSON, EUnits.Metric);
        //AsyncCaller asyncCaller=new AsyncCaller(caller, AsyncCaller.AsyncMethod.byCityId,3600000, )
        userSettings.interfaceSettings.guiEnabled = true;
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
