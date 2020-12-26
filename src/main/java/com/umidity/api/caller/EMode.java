package com.umidity.api.caller;

/**
 * Enumeration for response mode. Generally, Umiditity will use JSON format, but you never know if an XML or HTML
 * will be needed in future.
 */
public enum EMode {
    /**
     * Get JSON responses
     */
    JSON(""),
    /**
     * Get XML responses
     */
    XML("xml"),
    /**
     * Get HTML responses
     */
    HTML("html");

    //di seguito, il codice necessario per impostare valori personalizzati al enumeratore
    private final String action;
    public String getAction()
    {
        return this.action;
    }
    EMode(String action)
    {
        this.action = action;
    }
}
