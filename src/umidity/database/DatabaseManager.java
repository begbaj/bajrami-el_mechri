package umidity.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import umidity.Main;
import umidity.UserSettings;

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
     * @param username given user's username
     */
    public void addHumidity(HumidityRecord humidityRecord, String username){
        final ObjectMapper objectMapper=new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        try {
                List<HumidityRecord> records=getHumidity(humidityRecord.getCity_id(), username);
                records.add(humidityRecord);
                writer.writeValue(Paths.get("users/"+username+"/"+humidityRecord.getCity_id()+".json").toFile(), records);
        }
        catch (MismatchedInputException e){ //Il file era vuoto
            try {
                List<HumidityRecord> records = new ArrayList<>();
                records.add(humidityRecord);
                writer.writeValue(Paths.get("users/"+username+"/"+humidityRecord.getCity_id() + ".json").toFile(), records);
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
     * @param username given user's username
     * @return list of all wanted record
     */
    public List<HumidityRecord> getHumidity(int city_id, String username){
        List<HumidityRecord> records=new ArrayList<>();
            try {
                final ObjectMapper objectMapper=new ObjectMapper();
                records=objectMapper.readValue(new File("users/"+username+"/"+city_id+".json"), new TypeReference<List<HumidityRecord>>(){});
            }
            catch (MismatchedInputException e){ //Non trova alcun json(File vuoto)
            return records;
            }
            catch (FileNotFoundException e){ //Non trova alcun file
                try {
                    File yourFile = new File("users/"+username +"/"+city_id + ".json");
                    yourFile.createNewFile(); //Perciò crea il file
                    return records;
                }catch (Exception ex){
                    e.printStackTrace();
                }
            }
            catch (Exception ex) {
            ex.printStackTrace();
            }
        //records.forEach(x -> System.out.println(x.toString()));
        return records;
    }

    /**
     * Loads given user's settings
     * @param username user's username
     */
    public void loadUserSettings(String username){
        //Carica preferenze utente nella classe statica UserSettings
        Properties prop=new Properties();
        try {
            FileInputStream config= new FileInputStream("users" +"/"+username+"/config.properties");
            prop.load(config);
        }
        catch (FileNotFoundException e){
            File yourFile = new File("users/"+username+"config.properties");
            try{
                yourFile.createNewFile();
                FileWriter myWriter = new FileWriter("users/"+username+"config.properties");
                myWriter.write(
                        "guiUserTheme="+ Main.userSettings.interfaceSettings.guiUserTheme+"\n"
                        +"cliUserTheme="+Main.userSettings.interfaceSettings.cliUserTheme+"\n"
                        +"guiEnabled="+Main.userSettings.interfaceSettings.guiEnabled);
                myWriter.close();
                FileInputStream config= new FileInputStream("users" +"/"+username+"/config.properties");
                prop.load(config);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        Main.userSettings.interfaceSettings.guiUserTheme=prop.getProperty("guiUserTheme");
        Main.userSettings.interfaceSettings.cliUserTheme=prop.getProperty("cliUserTheme");
        Main.userSettings.interfaceSettings.guiEnabled=Boolean.parseBoolean(prop.getProperty("guiEnabled"));
        System.out.println(Main.userSettings.interfaceSettings.guiUserTheme);
        System.out.println(Main.userSettings.interfaceSettings.cliUserTheme);
        System.out.println(Main.userSettings.interfaceSettings.guiEnabled);
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
