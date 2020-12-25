package com.umidity.api;


import com.umidity.Debugger;
import com.umidity.api.response.ApiIResponse;
import com.umidity.api.response.EExclude;
import com.umidity.api.response.ForecastIResponse;
import com.umidity.api.response.OneCallIResponse;
import org.jetbrains.annotations.Debug;

import java.io.IOException;
import java.util.EnumSet;

public class AsyncCaller extends Thread {
    public enum AsyncMethod{
        oneCall1, oneCall2,
        byCityName, byCityId, byCoordinates, byZipCode,
        forecastByCityName, forecastByCityId, forecastByCoordinates, forecastByZipCode,
    }

    public boolean close;
    public OneCallIResponse oneCallIResponse;
    public ApiIResponse apiIResponse;
    public ForecastIResponse forecastIResponse;
    private ApiCaller caller;
    private AsyncMethod method;
    private long ms;
    private Object[] args;

    public AsyncCaller(ApiCaller caller, AsyncMethod method, long ms, Object... args) {
        this.caller = caller;
        this.method = method;
        this.ms = ms;
        this.args = args;
    }

    public void close(){ close = true; }

    public void run()
    {
        try {
            while(!close){
                switch (method){
                    case oneCall1 ->
                            oneCallIResponse = caller.oneCall((float)args[0],
                                    (float)args[1], (EnumSet<EExclude>)args[2]);
                    case oneCall2 ->
                            oneCallIResponse =caller.oneCall((float)args[0],
                                    (float)args[1], (long)args[2], (EnumSet<EExclude>) args[3]);
                    case byCityName ->
                            apiIResponse = caller.getByCityName((String)args[0],
                                    (String)args[1], (String)args[2]);
                    case byCoordinates ->
                            apiIResponse = caller.getByCoordinates((float)args[0],
                                    (float)args[1]);
                    case byCityId ->
                            apiIResponse = caller.getByCityId((String)args[0]);
                    case byZipCode ->
                            apiIResponse = caller.getByZipCode((String)args[0], (String)args[1]);
                    case forecastByCityId ->
                            forecastIResponse = caller.getForecastByCityId((String)args[0]);
                    case forecastByZipCode ->
                            forecastIResponse = caller.getForecastByZipCode((String)args[0],
                                    (String)args[1]);
                    case forecastByCityName ->
                            forecastIResponse = caller.getForecastByCityName((String)args[0],
                                    (String) args[1],(String)args[1]);
                    case forecastByCoordinates ->
                            forecastIResponse = caller.getForecastByCoordinates((float)args[0],
                                    (float)args[1]);
                    }
                    Thread.sleep(ms);
                }
            } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            Debugger.println("AsyncCaller fermato!");
        }
    }
}
