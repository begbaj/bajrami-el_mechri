package com.umidity.api.caller;

/**
 * Enumeration for units. This is needed when an Api call is made to determine the used units by Openweather.
 * Standard: Standard units applied in the location you're looking for
 * Metric: metric units (meter, celsius, ...)
 * Imperial: imperial unuits (miles, Fahrenheit, ...)
 */
public enum EUnits {
    /**
     * Standard units applied in the location you're looking for
     */
    Standard(""),
    /**
     * metric units (meter, celsius, ...)
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
