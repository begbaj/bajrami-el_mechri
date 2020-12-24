package com.umidity.api;

/**
 * Enumeration for units. This is needed when an Api call is made to determine the used units by Openweather.
 * Standard //TODO: vallo a cercare
 * Metric: metric units (meter, celsius, ...)
 * Imperial: imperial unuits (miles, Fahrenheit, ...)
 */
public enum EUnits {
    Standard(""),
    /**
     *  metric units (meter, celsius, ...)
     */
    Metric("metric"),
    /**
     * imperial unuits (miles, Fahrenheit, ...)
     */
    Imperial("imperial");

    //di seguito, il codice necessario per impostare valori personalizzati al enumeratore
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
