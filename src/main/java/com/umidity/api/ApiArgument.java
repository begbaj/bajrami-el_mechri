package com.umidity.api;

import java.security.Timestamp;
import java.util.Date;

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
