package umidity.database;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import umidity.Main;
import umidity.api.response.Coordinates;
import umidity.api.response.ICoordinates;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;


public class DatabaseManager {

//    /**
//     * Adds a humidity record to the given user's database
//     *
//     * @param record record to save
//     * @param filename   the path we're saving the record in
//     */
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

    public boolean addCity(CityRecord cityRecord){
        final ObjectMapper objectMapper=new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        boolean flag=true;
        try {
            List<CityRecord> records=getCities();
            for(CityRecord record:records)
            {
                if(record.getId()==cityRecord.getId())
                    flag=false;
            }
            if(flag) {
                records.add(cityRecord);
                writer.writeValue(Paths.get("records/cities.json").toFile(), records);
            };
        }
        catch (MismatchedInputException e){ //Il file era vuoto
            try {
                List<CityRecord> records = new ArrayList<>();
                records.add(cityRecord);
                writer.writeValue(Paths.get("records/cities.json").toFile(), records);
            }
            catch (Exception ex){
                flag=false;
                ex.printStackTrace();
            }
        }
        catch (IOException e) {
            flag=false;
            e.printStackTrace();
        }
        return flag;
    }

    public void setFavouriteCity(CityRecord cityRecord){
        final ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(Paths.get("records/favourite.json").toFile(), cityRecord);
        }catch (FileNotFoundException ex){
            File yourFile = new File("records/favourite.json");
            yourFile.getParentFile().mkdirs(); //CREA LE DIRECTORY SOPRA
            try {
                yourFile.createNewFile();
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public CityRecord getFavouriteCity(){
        Coordinates coord=new Coordinates(-1, -1);
        CityRecord cityRecord=new CityRecord(-1, "", coord);
        final ObjectMapper objectMapper=new ObjectMapper();
        try{
            cityRecord = objectMapper.readValue(new File("records/favourite.json"), new TypeReference<CityRecord>() {
            });
        }catch (FileNotFoundException e){
                File yourFile = new File("records/favourite.json");
                yourFile.getParentFile().mkdirs(); //CREA LE DIRECTORY SOPRA
            try {
                yourFile.createNewFile();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityRecord;
    }

    public boolean removeCity(CityRecord cityRecord) {
        final ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        boolean flag = false;
        List<CityRecord> records = getCities();
        Iterator itr = records.iterator();
        while (itr.hasNext())
        {
            CityRecord record=(CityRecord) itr.next();
            if(record.getId()==cityRecord.getId()) {
                itr.remove();
                flag = true;
            }
        }
        if(flag) {
            try {
                writer.writeValue(Paths.get("records/cities.json").toFile(), records);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public boolean cityisSaved(CityRecord city){
        List<CityRecord> records = getCities();
        for (CityRecord record : records) {
            if (record.getId() == city.getId()) {
                return true;
            }
        }
        return false;
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
            return records;
        }
        catch (MismatchedInputException e){ //Non trova alcun json(File vuoto)
            return records;
        }
        catch (FileNotFoundException e){ //Non trova alcun file
            try {
                File yourFile = new File("records/"+city_id + ".json");
                yourFile.getParentFile().mkdirs(); //CREA LE DIRECTORY SOPRA
                yourFile.createNewFile(); //Perciò crea il file
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

    public List<CityRecord> getCities(){
            List<CityRecord> records = new ArrayList<>();
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            records = objectMapper.readValue(new File("records/cities.json"),
                    new TypeReference<List<CityRecord>>() {});
        } catch (MismatchedInputException e) { //Non trova alcun record(File vuoto)
            return records;
        } catch (FileNotFoundException e) { //Non trova alcun file
            try {
                File yourFile = new File("records/cities.json");
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

    //TODO: RICORDATI DI FARLO
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

