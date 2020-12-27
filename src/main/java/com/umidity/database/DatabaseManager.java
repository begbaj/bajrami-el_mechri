package com.umidity.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.umidity.Debugger;
import com.umidity.Main;
import com.umidity.UserSettings;
import com.umidity.Coordinates;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class DatabaseManager {

    final ObjectMapper objectMapper=new ObjectMapper();
    final ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
    private String basePath;

    public DatabaseManager(){
        basePath = "records/";
    }
    public DatabaseManager(String newPath){
        newPath = newPath.trim();
        if(newPath.charAt(newPath.length()-1) != '/')
            newPath += "/";
        basePath = newPath;
    }

    /**
     * Delete a file or a directory in the given path. If it is a directory,
     * the method will delete every file in it, including the directory itself.
     * @param path
     */
    public void delete(String path){
        File file = new File(path);
        if(file.isDirectory()){
            if(file.list().length == 0){
                file.delete();
                Debugger.println("Deleting folder : " + file.getAbsolutePath());
            }else{
                //list all the files in directory
                File files[] = file.listFiles();
                for (File temp : files) {
                    //recursive delete
                    try {
                        Files.delete(Path.of(temp.getPath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // Check directory again, if we find it empty, delete it
                if(file.list().length==0){
                    file.delete();
                    Debugger.println("Deleting folder : "
                            + file.getAbsolutePath());
                }
            }
        }else{
            //if file, then we can directly delete it
            file.delete();
            Debugger.println("Deleting file  : " + file.getAbsolutePath());
        }
    }

    public void deleteFile(String path){
        File cityRecordsFile = new File(path);
        cityRecordsFile.delete();
    }
    /**
     * This method creates a new Database deleting, if existing, the one present in the indicated <em>basePath</em>
     * (default is "records/")
     */
    public void recreate(){
        if(Files.exists(Path.of(basePath))){
            delete(basePath);
        }
        try {
            Files.createDirectory(Path.of(basePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addCity(CityRecord cityRecord){
        HashSet<CityRecord> cities = new HashSet<>();
        for(var c:getCities()){
            cities.add(c);
        }
        cities.add(cityRecord);
        try {
            writer.writeValue(Paths.get(basePath + "cities.json").toFile(), cities.toArray());
        } catch (MismatchedInputException e){ //Il file era vuoto
            try {
                List<CityRecord> records = new ArrayList<>();
                records.add(cityRecord);
                writer.writeValue(Paths.get(basePath+"cities.json").toFile(), records);
            }
            catch (Exception ex){
                ex.printStackTrace();
                return false;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<CityRecord> getCities(){
        List<CityRecord> records = new ArrayList<>();
        try {
            records = objectMapper.readValue(new File(basePath + "cities.json"), new TypeReference<List<CityRecord>>() {});
        } catch (MismatchedInputException e) { //Non trova alcun record(File vuoto)
            return records;
        } catch (FileNotFoundException e) { //Non trova alcun file
            try {
                createNewFile(basePath + "cities.json");
                return records;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return records;
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
                deleteFile(basePath+cityRecord.getId()+".json");
                flag = true;
            }
        }
        if(flag) {
            try {
                writer.writeValue(Paths.get(basePath + "cities.json").toFile(), records);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public void setFavouriteCity(CityRecord cityRecord){
        try {
            writer.writeValue(Paths.get(basePath + "favourite.json").toFile(), cityRecord);
        }catch (FileNotFoundException ex){
            createNewFile(basePath + "favourite.json");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public CityRecord getFavouriteCity(){
        CityRecord cityRecord=new CityRecord(-1, "", new Coordinates(-1, -1));
        try{
            cityRecord = objectMapper.readValue(new File( basePath + "favourite.json"), new TypeReference<CityRecord>(){});
        }catch (FileNotFoundException e){
            createNewFile(basePath + "favourite.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityRecord;
    }

    public void addHumidity(HumidityRecord humidityRecord){
        HashSet<HumidityRecord> records = new HashSet<>();
        try {
            for(var r:getHumidity(humidityRecord.getCity().getId())){
                records.add(r);
            };
            records.add(humidityRecord);
            writer.writeValue(Paths.get(basePath +humidityRecord.getCity().getId()+".json").toFile(), records);
        }
        catch (MismatchedInputException e){
            try {
                records = new HashSet<>();
                records.add(humidityRecord);
                writer.writeValue(Paths.get(basePath+humidityRecord.getCity().getId() + ".json").toFile(), records);
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
            records=objectMapper.readValue(new File(basePath + city_id+".json"),
                    new TypeReference<List<HumidityRecord>>(){});
            return records;
        }
        catch (MismatchedInputException e){ //Non trova alcun json(File vuoto)
            return records;
        }
        catch (FileNotFoundException e){ //Non trova alcun file
            try {
                createNewFile(basePath+city_id + ".json");
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


    public boolean cityisSaved(CityRecord city){
        List<CityRecord> records = getCities();
        for (CityRecord record : records) {
            if (record.getId() == city.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Loads settings from config file
     */
    public void loadUserSettings() {
        try {
            Main.userSettings=objectMapper.readValue(new File(basePath + "config.json"), new TypeReference<UserSettings>(){});
        }catch (MismatchedInputException | FileNotFoundException e){
            deleteFile(basePath + "config.json");
            createNewFile(basePath + "config.json");
            setUserSettings();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Saves settings into file
     */
    public void setUserSettings() {
        createNewFile(basePath + "config.json");
        try {
            writer.writeValue(Paths.get(basePath + "config.json").toFile(), Main.userSettings);
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

}

