package com.umidity.api.caller;

/**
 * Api event listener interface.<br>
 *
 * Implement this interface whenever you need to handle ApiCaller events. This may find real usage when using an AsyncCaller.<br>
 * To add your listener to the "list of listeners" of an ApiCaller instance, just call the <em>addListener(yourListener)</em> method.
 */
public interface ApiListener {

    /**
     * Launched when <em>Current wheather information</em> are received
     * @param sender
     * @param arg
     */
    void onReceiveCurrent(Object sender, ApiArgument arg);
    /**
     * Launched when <em>Forecast wheather information</em> are received
     * @param sender
     * @param arg
     */
    void onReceiveForecast(Object sender, ApiArgument arg);
    /**
     * Launched when <em>Historical wheather information</em> are received
     * @param sender
     * @param arg
     */
    void onReceiveHistorical(Object sender, ApiArgument arg);
    /**
     * Launched when <em>Any wheather information</em> are received
     * @param sender
     * @param arg
     */
    void onReceive(Object sender, ApiArgument arg);


    /**
     * Launched when <em>Current wheather information</em> is requested
     * @param sender
     * @param arg
     */
    void onRequestCurrent(Object sender, ApiArgument arg);
    /**
     * Launched when <em>Forecast wheather information</em> is requested
     * @param sender
     * @param arg
     */
    void onRequestForecast(Object sender, ApiArgument arg);
    /**
     * Launched when <em>Historical wheather information</em> is requested
     * @param sender
     * @param arg
     */
    void onRequestHistorical(Object sender, ApiArgument arg);
    /**
     * Launched when <em>Any wheather information</em> is requested
     * @param sender
     * @param arg
     */
    void onRequest(Object sender, ApiArgument arg);

    void onException(Object sender, Exception e);
}
