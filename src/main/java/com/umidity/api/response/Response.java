package com.umidity.api.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umidity.api.Current;
import com.umidity.api.Forecast;
import com.umidity.api.Historical;

public abstract class Response {

    public Current getCurrent(){
        return null;
    }

    public Forecast getForecast(){
        return null;
    }

    public Historical getHistorical(){
        return null;
    }

    public Object getClone(){
        try{
            return this.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;
    }

}
