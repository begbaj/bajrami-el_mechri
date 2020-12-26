package com.umidity.api.caller;

import com.umidity.api.caller.ApiListener;

import java.util.ArrayList;

public abstract class Caller{
    ArrayList<ApiListener> apiListeners = new ArrayList<>();

    public void addListener(ApiListener listener){ apiListeners.add(listener); }
}
