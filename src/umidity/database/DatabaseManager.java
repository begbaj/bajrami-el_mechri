package umidity.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import umidity.Main;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;


public class DatabaseManager {

    /**
     * Adds a humidity record to the given user's database
     *
     * @param record record to save
     * @param filename   the path we're saving the record in
     */
//    public void addRecord(Object record, String filename) {
//        final ObjectMapper objectMapper = new ObjectMapper();
//        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
//        List<Object> records = null;
//        records = getRecords("Records/"+filename+".json");
//        records.add(record);
//        try {
//            writer.writeValue(Paths.get("Records/"+filename+".json").toFile(), records);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

    public void addCity(CityRecord record, String filename) {
        final ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        List<CityRecord> records = null;
        records = getCities("Records/"+filename+".json");
        records.add(record);
        try {
            writer.writeValue(Paths.get("Records/"+filename).toFile(), records);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addHumidity(HumidityRecord humidityRecord){
        final ObjectMapper objectMapper=new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        try {
            List<HumidityRecord> records=getHumidity(humidityRecord.getCity().getId());
            records.add(humidityRecord);
            writer.writeValue(Paths.get("records/"+humidityRecord.getCity().getId()+".json").toFile(), records);
        }
        catch (MismatchedInputException e){ //Il file era vuoto
            try {
                List<HumidityRecord> records = new ArrayList<>();
                records.add(humidityRecord);
                writer.writeValue(Paths.get("records/"+humidityRecord.getCity().getId() + ".json").toFile(), records);
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
            for (HumidityRecord x:records) {
                System.out.println(x.getHumidity());
            }
            return records;
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

    public List<CityRecord> getCities(String filename){
            List<CityRecord> records = new ArrayList<>();
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            records = objectMapper.readValue(new File("Records/"+filename+".json"), new TypeReference<List<CityRecord>>() {
            });
        } catch (MismatchedInputException e) { //Non trova alcun record(File vuoto)
            return records;
        } catch (FileNotFoundException e) { //Non trova alcun file
            try {
                File yourFile = new File("Records/"+filename+".json");
                yourFile.getParentFile().mkdirs(); //CREA LE DIRECTORY SOPRA
                yourFile.createNewFile(); //Perciò crea il file
                return records;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return records;
    }

    /**
     * Loads given user's settings
     */
    public void loadUserSettings() { //TODO: OTTIMIZZABILE AS FUCK, GESTISCI ECCEZZIONI PROPRIETA'(ES. NULL)
        try {
            Properties prop = new Properties();
            FileInputStream config = new FileInputStream("config.properties");
            prop.load(config);
            Main.userSettings.interfaceSettings.guiUserTheme = prop.getProperty("guiUserTheme");
            Main.userSettings.interfaceSettings.cliUserTheme = prop.getProperty("cliUserTheme");
            Main.userSettings.interfaceSettings.guiEnabled = Boolean.parseBoolean(prop.getProperty("guiEnabled"));
        } catch (FileNotFoundException e) {
            File yourFile = new File("config.properties");
            try {
                yourFile.createNewFile();
                FileWriter myWriter = new FileWriter("config.properties");
                myWriter.write(
                        "guiUserTheme=Light" + "\n"  //TODO: DECIDERE BENE COME GESTIRE
                                + "cliUserTheme=Light" + "\n"
                                + "guiEnabled=True");
                myWriter.close();
                FileInputStream config = new FileInputStream("config.properties");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //TODO: RICORDATI DI FARLO PER DIO
    public void setUserSettings() { //TODO: DEFAULT VS OBBLIGA A NON FARLO(MENU A TENDINA?)

    }

    /**
     * Gets a list of all users
     * @return A vector with all usernames in String format
     */
    @Deprecated
    public Vector<String> getUsersList() { //LEGGI I NOMI DELLE CARTELLE PRESENTI
        File[] directories = new File("users/").listFiles(File::isDirectory);
        Vector<String> names = new Vector<String>();
        for (File x : directories) {
            names.add(x.getName());
        }
        return names;
    }
}

