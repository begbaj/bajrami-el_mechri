package umidity.database;

import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.databind.*;
import umidity.information.WeatherInfo;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;



public class DatabaseManager {
    public void saveHumidity(WeatherInfo record){
        try {
            //i have WI object

            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // create an instance of DefaultPrettyPrinter
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

            // convert WeatherInfo object to JSON file
            writer.writeValue(Paths.get("DBFile.json").toFile(), record);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getHumidity(){
        try {
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // convert JSON array to list of WeatherInfos
            List<WeatherInfo> WeatherInfos = Arrays.asList(mapper.readValue(Paths.get("DBFile.json").toFile(), WeatherInfo[].class));

            // print Humidity infos
            WeatherInfos.forEach(System.out::println);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
