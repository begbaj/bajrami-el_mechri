package umidity.api;

import java.util.Formatter;
import java.util.Map;

public class ApiCalls {
//questa classe contiene il necessario per effettuare le chiamate alle api di OpenWeather
    private Map<String,String> apiStrings;
    private final String bycityname;
    private final String bycityid;
    private final String bycoordinates;
    private final String byzipcode;

    private final String inrectangle;
    private final String incircle;
    private final String severalids;


    public ApiCalls(){
        bycityname = "api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";
        bycityid = "api.openweathermap.org/data/2.5/weather?id=%s&appid=%s";
        bycoordinates = "api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s";
        byzipcode = "api.openweathermap.org/data/2.5/weather?zip=%s&appid=%s";

        inrectangle = "";
        incircle = "";
        severalids = "";
    }

    private String getBycityname(String query, String extra){
        return new Formatter().format(bycityname, query, extra).toString();
    }



}
