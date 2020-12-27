package com.umidity.api.caller;

import java.util.Date;

/**
 * ApiArgument
 * @param <T>
 */
public class ApiArgument<T>{
    private long eventLaunchedAt;
    private T response;

    public ApiArgument(T response){
        this.response = response;
        eventLaunchedAt = new Date().getTime();
    }

    public long getEventLaunchedAt(){ return eventLaunchedAt;}
    public T getResponse(){return response; }
}
