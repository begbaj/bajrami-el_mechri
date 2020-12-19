package umidity.api.response;

public class ForecastResponse {
    public String cod;
    public int message;
    public int cnt;
    public ApiResponse[] list;
    public class city{
        public long id;
        public String name;
        public Coordinates coord;
        public String country;
        public long population;
        public long timezone;
        public long sunrise;
        public long sunset;
    }
}
