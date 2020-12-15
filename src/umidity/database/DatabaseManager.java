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


public class DatabaseManager {

    /**
     *
     * @param humidityRecord
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

    //TODO: Gestione delle preferenze del utente
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
                        "guiUserTheme="+Main.userSettings.interfaceSettings.guiUserTheme+"\n"
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
    //TODO: per ogni utente, salvare le relative statistiche in una cartella rinominata con l'identificatore di esso
    /**
     * Get a list of the saved users
     */
    public void getUsersList(){
        //LEGGI I NOMI DELLE CARTELLE PRESENTI
    }
}
