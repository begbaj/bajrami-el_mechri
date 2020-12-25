package com.umidity.api;

import java.util.ArrayList;

public abstract class Caller{
    ArrayList<ApiListener> apiListeners = new ArrayList<>();

    public void addListener(ApiListener listener){ apiListeners.add(listener); }
}
