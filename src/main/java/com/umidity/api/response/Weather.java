package com.umidity.api.response;

public class Weather {
    /**
     * Weather condition id
     */
    public int id;
    /**
     * Group of weather parameters (Rain, Snow, Extreme etc.)
     */
    public String main;
    /**
     * Weather condition within the group. Output is in the indicated language in the <em>ApiCaller</em>
     */
    public String description;
    /**
     * Weather icon id
     */
    public String icon;
}
