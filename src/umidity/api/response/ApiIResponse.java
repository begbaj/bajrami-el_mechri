package umidity.api.response;



public class ApiIResponse implements IResponse, ICoordinates, IHumidity {
    //TODO: documentazione necessaria
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
    public Coordinates getCoord() {
        return coord.getCoord();
    }

    @Override
    public int getHumidity() {
        return main.humidity;
    }
}
