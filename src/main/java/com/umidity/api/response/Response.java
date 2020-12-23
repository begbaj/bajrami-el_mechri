package com.umidity.api.response;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Response {
    public Object getClone(){
        try{
            return this.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;
    }

    //public getJson();
    //restituisce solo le informazioni importanti per la gui
    //public getRelevant();

}
