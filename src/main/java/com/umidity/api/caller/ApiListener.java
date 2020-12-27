package com.umidity.api.caller;

public interface ApiListener {

    void onReceiveCurrent(Object sender, ApiArgument arg);
    void onReceiveForecast(Object sender, ApiArgument arg);
    void onReceiveHistorical(Object sender, ApiArgument arg);
    void onReceive(Object sender, ApiArgument arg);

    void onRequestCurrent(Object sender, ApiArgument arg);
    void onRequestForecast(Object sender, ApiArgument arg);
    void onRequestHistorical(Object sender, ApiArgument arg);
    void onRequest(Object sender, ApiArgument arg);

}
