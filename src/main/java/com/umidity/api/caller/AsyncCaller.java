package com.umidity.api.caller;


import com.umidity.Debugger;
import com.umidity.api.response.ApiResponse;
import com.umidity.api.response.ForecastResponse;
import com.umidity.api.response.OneCallResponse;

import java.io.IOException;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.Vector;

public class AsyncCaller extends Thread {
    public enum AsyncMethod{
        oneCall1, oneCall2,
        byCityName, byCityId, byCoordinates, byZipCode,
        forecastByCityName, forecastByCityId, forecastByCoordinates, forecastByZipCode,
    }

    private boolean close;
    private boolean isRunning;
    private boolean oneTime;
    public  Vector<OneCallResponse> oneCallResponse;
    public  Vector<ApiResponse> apiResponse;
    public  Vector<ForecastResponse> forecastResponse;
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

    public void run() {
        long lastExecution = 0;
        try {
            while(!close){
                isRunning = true;
                oneCallResponse.clear();
                apiResponse.clear();
                forecastResponse.clear();
                if(Calendar.getInstance().getTimeInMillis() - lastExecution >= ms){

                    switch (method){
                        case oneCall1 ->
                                oneCallResponse.add(caller.oneCall((float)args[0], (float)args[1], (EnumSet<EExclude>)args[2]));
                        case oneCall2 ->
                                oneCallResponse.add(caller.oneCall((float)args[0],
                                        (float)args[1], (long)args[2], (EnumSet<EExclude>) args[3]));
                        case byCityName ->
                                apiResponse.add(caller.getByCityName((String)args[0],
                                        (String)args[1], (String)args[2]));
                        case byCoordinates ->
                                apiResponse.add(caller.getByCoordinates((float)args[0],
                                        (float)args[1]));
                        case byCityId -> getByCityId((int[])args[0]);
                        case byZipCode ->
                                apiResponse.add(caller.getByZipCode((String)args[0], (String)args[1]));
                        case forecastByCityId ->
                                forecastResponse.add(caller.getForecastByCityId((String)args[0]));
                        case forecastByZipCode ->
                                forecastResponse.add(caller.getForecastByZipCode((String)args[0],
                                        (String)args[1]));
                        case forecastByCityName ->
                                forecastResponse.add(caller.getForecastByCityName((String)args[0],
                                        (String) args[1],(String)args[1]));
                        case forecastByCoordinates ->
                                forecastResponse.add(caller.getForecastByCoordinates((float)args[0],
                                        (float)args[1]));
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

    /**
     * Makes call to "By City Id" api. If there are more than one argument, it will make a call for each argument.
     * @param args a list of city ids (a list of Strings)
     * @throws IOException
     */
    private void getByCityId(int[] args) throws IOException {
        apiResponse.clear();
        for(Object o:args){
            apiResponse.add(caller.getByCityId(String.valueOf(o)));
        }
    }
}
