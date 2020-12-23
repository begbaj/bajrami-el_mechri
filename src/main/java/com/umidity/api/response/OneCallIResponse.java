package com.umidity.api.response;

public class OneCallIResponse extends Response implements IResponse, ICoordinates, IHumidity {
    float lat;
    float lon;
    String timezone;
    long timezone_offset;
    Current current;
    Minutely[] minutely;
    Hourly[] hourly;
    Daily[] daily;
    Alerts[] alerts;

    @Override
    public int getHumidity() {
        return current.humidity;
    }

    public class Current{
        long dt;
        long sunrise;
        long sunset;
        float temp;
        float feels_like;
        int pressure;
        int humidity;
        float dew_point;
        int clouds;
        int visibility;
        int wind_speed;
        int wind_deg;
        Weather[] weather;
    }

    public class Minutely{
        long dt;
        int precipitation;
    }

    public class Hourly{
        long dt;
        float temp;
        float feels_like;
        int pressure;
        int humidty;
        float dew_point;
        int uvi;
        int clouds;
        int visibility;
        float wind_speed;
        int wind_deg;
        Weather[] weather;
        float pop;
    }

    public class Daily{
        long dt;
        long sunrise;
        long sunset;
        Temp temp;
        Temp feels_like;
        int pressure;
        int humidity;
        float dew_point;
        float wind_speed;
        int wind_deg;
        Weather[] weather;
        int clouds;
        float pop;
        float uvi;

        public class Temp{
            float day;
            float min;
            float max;
            float night;
            float eve;
            float morn;
        }
    }

    public class Alerts{
        String sender_name;
        String event;
        long start;
        long end;
        String description;
    }



    @Override
    public Coordinates getCoord() {
        return new Coordinates(lat,lon);
    }
}
