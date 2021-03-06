package com.umidity.api.caller;

import java.util.ArrayList;

/**
 * Abstract class for every ApiCaller in the program. Currently, there is only ApiCaller, but may be useful for future implementations.<br>
 * This provides a list of ApiListeners for any Caller.
 */
public abstract class Caller{
    ArrayList<ApiListener> apiListeners = new ArrayList<>();

    public void addListener(ApiListener listener){ apiListeners.add(listener); }
}
