package com.umidity.api;

import com.umidity.api.response.ApiResponse;
import com.umidity.api.response.ForecastResponse;
import com.umidity.api.response.OneCallResponse;

public interface ApiListener {

    void onReciveCurrent(Object sender, ApiArgument arg);
    void onReciveForecast(Object sender, ApiArgument arg);
    void onReciveHistorical(Object sender, ApiArgument arg);
    void onRecive(Object sender, ApiArgument arg);

    void onRequestCurrent(Object sender, ApiArgument arg);
    void onRequestForecast(Object sender, ApiArgument arg);
    void onRequestHistorical(Object sender, ApiArgument arg);
    void onRequest(Object sender, ApiArgument arg);

}
