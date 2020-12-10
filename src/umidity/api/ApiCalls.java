package umidity.api;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.concurrent.Executors;
/**
 * This class handles every connection to the apis.
 */
public class ApiCalls {
//questa classe contiene il necessario per effettuare le chiamate alle api di OpenWeather

    /**
     * Api key used for making api calls
     */
    private String appid;
    private HttpClient client;


    public ApiCalls(String appid) throws NoSuchAlgorithmException {
        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2) //Versione di http utilizzata
                .proxy(ProxySelector.getDefault()) //Usa il proxy di sistema, se impostato
                .followRedirects(HttpClient.Redirect.NEVER) //Non seguire redirects
                .executor(Executors.newFixedThreadPool(2)) //?
                .priority(1) //?
                .sslContext(SSLContext.getDefault()) //?
                .sslParameters(new SSLParameters()) //?
                .connectTimeout(Duration.ofSeconds(1)) //Timeout
                .build();
        //Creo un nuovo client che verrà usato per ogni chiamata all'api. è davvero il modo migliore per farlo?
    }

     public String getByCityName(String cityName, String stateCode, String countryCode) throws URISyntaxException {
        String url = "api.openweathermap.org/data/2.5/weather?q="
                + cityName
                + (!stateCode.equals("") ? "," + stateCode : "")
                + (!countryCode.equals("") ? "," + countryCode : "")
                + "&appid=" + appid;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Accept", "application/json")
                .build();

        return "";
    }
    public String getByCityId(String cityId){
        return "api.openweathermap.org/data/2.5/weather?id=" + cityId + "&appid=" + appid;
    }
    public String getByCityIds(String[] cityIds){
        String apicall = "api.openweathermap.org/data/2.5/weather?id=";
        boolean first = true;
        for(String s:cityIds){
            if(!first) apicall += ",";
            apicall += s;
            first = false;
        }
        apicall += "&appid=" + appid;
        return apicall;
    }
    public String getByCoordinates(String lat, String lon){
        return "api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + appid;
    }
    public String getByZipCode(String zipCode, String countryCode){
        return "api.openweathermap.org/data/2.5/weather?zip=" + zipCode
                + (!countryCode.equals("")? "," + countryCode : "" )
                + "&appid=" + appid;
    }
//    public static String getInRectangle(){ //funziona con il boundingbox, non penso lo ueseremo mai
//        return = "api.openweathermap.org/data/2.5/box/city?bbox={bbox}&appid={API key}";
//        return apicall;
//    }

//    public static String getInCircle(IQuery query, String appid, IParameters params){ // non credo ci servirà mai
//        String apicall = "api.openweathermap.org/data/2.5/box/city?bbox=%s&appid=%s";
//        return apicall;
//    }


    //Getters and setters
    public String getAppid(){ return appid;}
}
