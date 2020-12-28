package com.umidity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.umidity.api.caller.*;
import com.umidity.api.response.ApiResponse;
import com.umidity.cli.MainCli;
import com.umidity.gui.*;
import com.umidity.database.DatabaseManager;
import org.jfree.chart.util.ArrayUtils;

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

    //TODO: apicaller comune [fatto]
    //TODO: api key nascosta [fatto, eccezioni?]
    //TODO: Main asyncaller [fatto]
    //TODO: Maingui eventi asyncaller
    //TODO: Maingui Utilizza Single [fatto]
    //TODO: Depreca cose inutili
    //TODO: Gestisci eccezioni grafici [fatto]
    //TODO: Tema grafici [fatto]
    //TODO: Sistema search [fatto]
    //TODO: Date easter egg [fatto]
    //TODO: OneCall utilizzo??
    //TODO: Humidity Record TimeStamp [fatto!]

    //TODO: PUSH(


    public static void main(String[] args){
        Debugger.setActive(true); //TODO: da rimuovere in release

        dbms.loadUserSettings();
        caller = new ApiCaller(userSettings.getApikey(), EUnits.Metric);
        Vector<Integer> ids = new Vector<>();
        for(var city:dbms.getCities()){
            ids.add(city.getId());
        }
        asyncCaller = new AsyncCaller(caller, 3600000, AsyncCaller.AsyncMethod.byCityId, (Object) ids.toArray(Integer[]::new));

        userSettings.setGuiEnabled(true);
        if(userSettings.isGuiEnabled()){
            new MainFrame();
        }else{
            new MainCli().run();
            Debugger.println("chiuso");
        }
    }

}
