package com.umidity.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.umidity.Main;
import com.umidity.UserSettings;
import com.umidity.api.response.Coordinates;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;


public class DatabaseManager {

    final ObjectMapper objectMapper=new ObjectMapper();
    final ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());

    public boolean addCity(CityRecord cityRecord){
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
        try {
            writer.writeValue(Paths.get("records/favourite.json").toFile(), cityRecord);
        }catch (FileNotFoundException ex){
            createNewFile("records/favourite.json");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public CityRecord getFavouriteCity(){
        CityRecord cityRecord=new CityRecord(-1, "", new Coordinates(-1, -1));
        try{
            cityRecord = objectMapper.readValue(new File("records/favourite.json"), new TypeReference<CityRecord>(){});
        }catch (FileNotFoundException e){
            createNewFile("records/favourite.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityRecord;
    }

    public boolean removeCity(CityRecord cityRecord) {
        boolean flag = false;
        List<CityRecord> records = getCities();
        Iterator itr = records.iterator();
        while (itr.hasNext())
        {
            CityRecord record=(CityRecord) itr.next();
            if(record.getId()==cityRecord.getId()) {
                itr.remove();
                deleteFile("records/"+cityRecord.getId()+".json");
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
        try {
            List<HumidityRecord> records=getHumidity(humidityRecord.getCity().getId());
            records.add(humidityRecord);
            writer.writeValue(Paths.get("records/"+humidityRecord.getCity().getId()+".json").toFile(), records);
        }
        catch (MismatchedInputException e){
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
     * Return all humidity record of the city(with *city_id* as id)
     * @param city_id city's id
     * @return list of all wanted record
     */
    public List<HumidityRecord> getHumidity(int city_id){
        List<HumidityRecord> records=new ArrayList<>();
        try {
            records=objectMapper.readValue(new File("records/"+city_id+".json"), new TypeReference<List<HumidityRecord>>(){});
            return records;
        }
        catch (MismatchedInputException e){ //Non trova alcun json(File vuoto)
            return records;
        }
        catch (FileNotFoundException e){ //Non trova alcun file
            try {
                createNewFile("records/"+city_id + ".json");
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
            records = objectMapper.readValue(new File("records/cities.json"), new TypeReference<List<CityRecord>>() {});
        } catch (MismatchedInputException e) { //Non trova alcun record(File vuoto)
            return records;
        } catch (FileNotFoundException e) { //Non trova alcun file
            try {
                createNewFile("records/cities.json");
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
     * Loads settings from config file
     */
    public void loadUserSettings() {
        try {
            Main.userSettings=objectMapper.readValue(new File("records/favourite.json"), new TypeReference<UserSettings>(){});
        }catch (MismatchedInputException | FileNotFoundException e){
            deleteFile("records/config.json");
            createNewFile("records/config.json");
            setUserSettings();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Saves settings into file
     */
    public void setUserSettings() {
        createNewFile("records/config.json");
        try {
            writer.writeValue(Paths.get("records/config.json").toFile(), Main.userSettings);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createNewFile(String path){
        File yourFile = new File(path);
        yourFile.getParentFile().mkdirs(); //CREA LE DIRECTORY SOPRA
        try {
            yourFile.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteFile(String path){
        File cityRecordsFile = new File(path);
        cityRecordsFile.delete();
    }
}

