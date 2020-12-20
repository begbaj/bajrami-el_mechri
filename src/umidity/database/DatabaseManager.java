package umidity.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import umidity.Main;
import umidity.api.response.Sys;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;


public class DatabaseManager {
    /**
     * Adds a humidity record to the given user's database
     * @param humidityRecord record to save
     */
    public void addHumidity(HumidityRecord humidityRecord){
        final ObjectMapper objectMapper=new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        try {
                List<HumidityRecord> records=getHumidity(humidityRecord.getCityId());
                records.add(humidityRecord);
                writer.writeValue(Paths.get("records/"+humidityRecord.getCityId()+".json").toFile(), records);
        }
        catch (MismatchedInputException e){ //Il file era vuoto
            try {
                List<HumidityRecord> records = new ArrayList<>();
                records.add(humidityRecord);
                writer.writeValue(Paths.get("records/"+humidityRecord.getCityId() + ".json").toFile(), records);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return all humidity record of the city(with *city_id* as id) that a certain user saved
     * @param city_id city's id
     * @return list of all wanted record
     */
    public List<HumidityRecord> getHumidity(int city_id){
        List<HumidityRecord> records=new ArrayList<>();
            try {
                final ObjectMapper objectMapper=new ObjectMapper();
                records=objectMapper.readValue(new File("records/"+city_id+".json"), new TypeReference<List<HumidityRecord>>(){});
                System.out.println("ESISTE");
            }
            catch (MismatchedInputException e){ //Non trova alcun json(File vuoto)
                System.out.println("DIOPORCO");
            return records;
            }
            catch (FileNotFoundException e){ //Non trova alcun file
                try {
                    File yourFile = new File("records/"+city_id + ".json");
                    yourFile.getParentFile().mkdirs(); //CREA LE DIRECTORY SOPRA
                    yourFile.createNewFile(); //Perciò crea il file
                    System.out.println("HO CREATO IL FILE");
                    return records;
                }catch (Exception ex){
                    e.printStackTrace();
                }
            }
            catch (Exception ex) {
            ex.printStackTrace();
            }
        return records;
    }

    /**
     * Loads given user's settings
     * @param username user's username
     */
    public void loadUserSettings(String username){ //TODO: OTTIMIZZABILE AS FUCK, GESTISCI ECCEZZIONI PROPRIETA'(ES. NULL)
        //Carica preferenze utente nella classe statica UserSettings
        try {
            Properties prop=new Properties();
            FileInputStream config= new FileInputStream("users" +"/"+username+"/config.properties");
            prop.load(config);
            Main.userSettings.interfaceSettings.guiUserTheme=prop.getProperty("guiUserTheme");
            Main.userSettings.interfaceSettings.cliUserTheme=prop.getProperty("cliUserTheme");
            Main.userSettings.interfaceSettings.guiEnabled=Boolean.parseBoolean(prop.getProperty("guiEnabled"));
        }
        catch (FileNotFoundException e){
            File yourFile = new File("users/"+username+"/config.properties");
            try{
                yourFile.createNewFile();
                FileWriter myWriter = new FileWriter("users/"+username+"/config.properties");
                myWriter.write(
                        "guiUserTheme=light"+"\n"  //TODO: DECIDERE BENE COME GESTIRE
                        +"cliUserTheme=light" +"\n"
                        +"guiEnabled=true");
                myWriter.close();
                FileInputStream config= new FileInputStream("users" +"/"+username+"/config.properties");
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void setUserSettings(String username){ //TODO: DEFAULT VS OBBLIGA A NON FARLO(MENU A TENDINA?)

    }

    /**
     *Gets a list of all users
     * @return A vector with all usernames in String format
     */
    public Vector<String> getUsersList(){ //LEGGI I NOMI DELLE CARTELLE PRESENTI
        File[] directories = new File("users/").listFiles(File::isDirectory);
        Vector<String> names=new Vector<String>();
        for (File x:directories) {
            names.add(x.getName());
        }
        return names;
    }
}
