package umidity.information;

public class ApiResponse {
    public Coordinates  coord;
    public Weather[]    weather;
    public String       base;
    public MainResponse main;
    public Wind         wind;
    public Clouds       clouds;
    public Rain         rain;
    public Snow         snow;
    public String       dt;
    public Sys          sys;
    public int          timezone;
    public int          id;
    public String       name;
    public int          cod;
    public int          visibility;

    @Override
    public String toString(){
        return "TEMPORARY:\n cityName: " + name + "\n Humidity: " + main.humidity;
    }
}
