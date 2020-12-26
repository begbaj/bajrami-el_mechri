package com.umidity.api.response;

import com.umidity.api.Single;

public abstract class Response {
    public Single getSingle(){ return null; }
    /**
     * Get array of <em>Single</em> objects
     * @return
     */
    public Single[] getSingles(){ return null; }

    public Object getClone(){
        try{
            return this.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;
    }
}
