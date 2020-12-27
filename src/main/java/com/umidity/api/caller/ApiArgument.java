package com.umidity.api.caller;

import com.umidity.api.Single;
import java.util.Calendar;
import java.util.Date;

/**
 * ApiArgument for event handling
 */
public class ApiArgument{
    /**
     * when was the event launched, in milliseconds from 00:00:00 UTC on 1 January 1970 (UNIX epoch)
     */
    private long eventLaunchedAt;
    /**
     * Responses of the api
     */
    private Single[] responses;

    public ApiArgument(){
        eventLaunchedAt = Calendar.getInstance().getTimeInMillis();
    }

    public ApiArgument(Single response){
        this.responses = new Single[]{response};
        eventLaunchedAt = Calendar.getInstance().getTimeInMillis();
    }
    public ApiArgument(Single[] responses){
        this.responses = responses;
        eventLaunchedAt = Calendar.getInstance().getTimeInMillis();
    }

    /**
     * when was the event launched, in milliseconds from 00:00:00 UTC on 1 January 1970 (UNIX epoch)
     * @return milliseconds from 00:00:00 UTC on 1 January 1970 (UNIX epoch)
     */
    public long getEventLaunchedAt(){ return eventLaunchedAt;}

    /**
     * Get the first <em>Single</em> responses in the array.
     * @return
     */
    public Single getResponse(){ return responses[0]; }
    /**
     * Get the array of <em>Single</em>s responses.
     * @return
     */
    public Single[] getResponses(){ return responses; }

}
