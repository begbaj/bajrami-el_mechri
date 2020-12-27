package com.umidity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.umidity.api.caller.*;
import com.umidity.api.response.ApiResponse;
import com.umidity.cli.MainCli;
import com.umidity.gui.*;
import com.umidity.database.DatabaseManager;

import java.util.Date;
import java.util.Vector;


public class Main{
    public static UserSettings userSettings = new UserSettings();
    public static DatabaseManager dbms = new DatabaseManager();
    public static ApiCaller caller;
    public static AsyncCaller asyncCaller;
    //TODO: unit tests [quasi]
    //TODO: JavaDocs [da fare]
    //TODO: README.md [da fare]

    //TODO: apicaller comune [fatto?]
    //TODO: api key nascosta [fatto? eccezioni?]
    //TODO: Main asyncaller [chiedi beg]
    //TODO: Maingui eventi asyncaller
    //TODO: Maingui Utilizza Single [fatto?]
    //TODO: Depreca cose inutili
    //TODO: Gestisci eccezioni grafici
    //TODO: Tema grafici
    //TODO: Sistema search [fatto?]
    //TODO: Inverti Date se al contrario?

    //TODO: PUSH(Fixed settings loading, fiex jdatepicker bug,


    public static void main(String[] args){
        Debugger.setActive(true); //TODO: da rimuovere in release
        Date time=new Date();
        dbms.loadUserSettings();
        caller = new ApiCaller(userSettings.apiSettings.apikey, EUnits.Metric);
        Vector<Integer> ids = new Vector<>();
        for(var city:dbms.getCities()){
            ids.add(city.getId());
        };
        asyncCaller = new AsyncCaller(caller, 3600000, AsyncCaller.AsyncMethod.byCityId, ids.toArray(Integer[]::new));

        userSettings.interfaceSettings.guiEnabled = true;
        if(userSettings.interfaceSettings.guiEnabled){
             MainFrame Frame=new MainFrame();
        }else{
            MainCli mainCli = new MainCli();
            mainCli.run();
            Debugger.println("chiuso");
        }
    }

}
