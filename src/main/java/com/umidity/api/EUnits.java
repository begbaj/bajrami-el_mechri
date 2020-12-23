package com.umidity.api;

public enum EUnits {
    Standard(""), Metric("metric"), Imperial("imperial");

    private final String action;
    public String getAction()
    {
        return this.action;
    }
    EUnits(String action)
    {
        this.action = action;
    }
}
