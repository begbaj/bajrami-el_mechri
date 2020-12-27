package com.umidity.api.caller;


import com.umidity.Debugger;
import com.umidity.api.response.ApiResponse;
import com.umidity.api.response.ForecastResponse;
import com.umidity.api.response.OneCallResponse;

import java.io.IOException;
import java.util.Calendar;
import java.util.EnumSet;

public class AsyncCaller extends Thread {
    public enum AsyncMethod{
        oneCall1, oneCall2,
        byCityName, byCityId, byCoordinates, byZipCode,
        forecastByCityName, forecastByCityId, forecastByCoordinates, forecastByZipCode,
    }

    private boolean close;
    private boolean isRunning;
    private boolean oneTime;
    public OneCallResponse oneCallResponse;
    public ApiResponse apiResponse;
    public  ForecastResponse forecastResponse;
    private ApiCaller        caller;
    private AsyncMethod      method;
    private long ms;
    private Object[] args;

    public boolean getRunningStatus(){return isRunning; }
    public void clearResponse(){
        oneCallResponse = null;
        apiResponse = null;
        forecastResponse = null;
    }

    public AsyncCaller(ApiCaller caller, AsyncMethod method, long ms, Object... args) {
        this.caller = caller;
        this.method = method;
        this.ms = ms;
        this.args = args;
        oneTime = false;
        isRunning = false;
    }

    public void addListener(ApiListener listener){caller.addListener(listener);}
    public void close(){ close = true; }
    public void setOneTime(boolean value){ oneTime = value; }

    public void run()
    {
        long lastExecution = 0;
        try {
            while(!close){
                isRunning = true;
                if(Calendar.getInstance().getTimeInMillis() - lastExecution >= ms){

                    switch (method){
                        case oneCall1 ->
                                oneCallResponse = caller.oneCall((float)args[0], (float)args[1], (EnumSet<EExclude>)args[2]);
                        case oneCall2 ->
                                oneCallResponse =caller.oneCall((float)args[0],
                                        (float)args[1], (long)args[2], (EnumSet<EExclude>) args[3]);
                        case byCityName ->
                                apiResponse = caller.getByCityName((String)args[0],
                                        (String)args[1], (String)args[2]);
                        case byCoordinates ->
                                apiResponse = caller.getByCoordinates((float)args[0],
                                        (float)args[1]);
                        case byCityId -> getByCityId(args);
                        case byZipCode ->
                                apiResponse = caller.getByZipCode((String)args[0], (String)args[1]);
                        case forecastByCityId ->
                                forecastResponse = caller.getForecastByCityId((String)args[0]);
                        case forecastByZipCode ->
                                forecastResponse = caller.getForecastByZipCode((String)args[0],
                                        (String)args[1]);
                        case forecastByCityName ->
                                forecastResponse = caller.getForecastByCityName((String)args[0],
                                        (String) args[1],(String)args[1]);
                        case forecastByCoordinates ->
                                forecastResponse = caller.getForecastByCoordinates((float)args[0],
                                        (float)args[1]);
                        }
                        if(oneTime)return;
                }
            }
            } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close = false;
            isRunning = false;
            Debugger.println("AsyncCaller fermato!");
        }
    }

    private void getByCityId(Object... args) throws IOException {
        if(args.length > 1){
            for(Object o:args){
                caller.getByCityId((String)o);
            }
        }
        else{
            apiResponse = caller.getByCityId((String)args[0]);
        }
    }
}
