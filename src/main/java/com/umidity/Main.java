package com.umidity;

import com.umidity.api.caller.ApiCaller;
import com.umidity.api.caller.AsyncCaller;
import com.umidity.api.caller.EUnits;
import com.umidity.cli.MainCli;
import com.umidity.database.DatabaseManager;
import com.umidity.gui.MainFrame;

import java.util.Vector;

/**
 * main entry to program.
 */
public class Main{
    public static UserSettings userSettings = new UserSettings();
    public static DatabaseManager dbms = new DatabaseManager();
    public static ApiCaller caller;
    public static AsyncCaller asyncCaller;

    public static void main(String[] args){
        Debugger.setActive(false);

        dbms.loadUserSettings();
        caller = new ApiCaller(userSettings.getApikey(), EUnits.Metric);
        Vector<Integer> ids = new Vector<>();
        for(var city:dbms.getCities()){
            ids.add(city.getId());
        }
        asyncCaller = new AsyncCaller(caller, 3600000, AsyncCaller.AsyncMethod.byCityId, (Object) ids.toArray(Integer[]::new));
        userSettings.setGuiEnabled(userSettings.isGuiEnabled());
        if(userSettings.isGuiEnabled()){
            asyncCaller.start();
            new MainFrame();
        }else{
            new MainCli().run();
            Debugger.println("chiuso");
        }
    }

}
