package umidity.cli;

import umidity.Debugger;
import umidity.Main;
import umidity.api.ApiCaller;
import umidity.api.EMode;
import umidity.api.EUnits;
import umidity.api.response.ApiResponse;
import umidity.cli.forms.FormManager;
import umidity.cli.forms.prompt.UserPromptTypes;

import java.util.Scanner;
import java.util.Vector;

public class MainCli extends FormManager {

    protected String prompt = ">"; //TODO: personalizzabile da un file per il tema
    protected ApiCaller caller = new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EMode.JSON, EUnits.Metric);
    //TODO: prendere appid da un file di configurazione
    private Scanner inputScanner;

    @Override
    protected void update(){
        System.out.println("prova");
    }




    private int mainMenu(){
        System.out.println(
                "Menu:\n" +
                        "1) Get by city name\n" +
                        "2) Get by city coordinates\n" +
                        "3) Get by city id\n" +
                        "4) Get ...\n" +
                        "0) to quit \n"
        );
        String input = userPrompt(UserPromptTypes.Integer);
        if(input.equals("1")){
            System.out.println("City name: ");
            ApiResponse response;
            try {
                String cityName = userPrompt();
                response = caller.getByCityName(cityName, "", "");
                System.out.printf(" City:%s\n Humidity:%s\n Temperature:%s \n",
                        response.name,
                        response.main.humidity,
                        response.main.temp);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(input.equals("2")){
            ApiResponse response;

            System.out.println("Latitude:");
            float lat = Float.parseFloat(userPrompt(UserPromptTypes.Float));
            System.out.println("Longitude:");
            float lon = Float.parseFloat(userPrompt(UserPromptTypes.Float));

            try{
                response = caller.getByCoordinates(lat,lon);
                System.out.printf(
                                " City:%s\n" +
                                " Humidity:%s\n" +
                                " Temperature:%s \n",
                        response.name,
                        response.main.humidity,
                        response.main.temp);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(input.equals("3")){
            System.out.println("City Id:");
            ApiResponse response;
            try{
                String cityid = userPrompt(UserPromptTypes.Integer);
                response = caller.getByCityId(cityid);
                System.out.printf(
                        " City:%s\n" +
                                " Humidity:%s\n" +
                                " Temperature:%s \n",
                        response.name,
                        response.main.humidity,
                        response.main.temp);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(input.equals("0"))
            return 0;
        return 1;
    }
    private int newUser(){
        return -1;
    }
    private int UserSelection(){
        Vector<String> userlist = Main.dbms.getUsersList();
        if(userlist.size() > 0){
            do{
                System.out.println("Sono stati trovati " + userlist.size() + " utenti. Selezionarne uno o crearne uno nuovo");
                int count = 0;
                for(String s:userlist){
                    System.out.println(++count + ") " + s);
                }
                System.out.println("0) Crea nuovo utente");
                String input = userPrompt(UserPromptTypes.Integer);
                if(input == "0"){
                    newUser();
                    return 0;
                }
                else {
                    try{
                        Main.dbms.loadUserSettings(userlist.elementAt(Integer.parseInt(input)));
                    }catch (IndexOutOfBoundsException e){
                        System.out.println("Elemento non valido! Riprovare");
                    }
                }
            }while(true);
        }
        return -1;
    }
    public String userPrompt(){
        System.out.print("string::" + prompt);
        String returns = inputScanner.nextLine();
        System.out.println();
        return returns;
    }
    public String userPrompt(UserPromptTypes promptType){
        String returns = "";
        if(promptType == UserPromptTypes.Integer){
            System.out.print("int::" + prompt);
            returns += String.valueOf(inputScanner.nextInt());
            inputScanner = new Scanner(System.in);
            System.out.println();
        }
        if(promptType == UserPromptTypes.Float){
            System.out.print("float::" + prompt);
            returns = String.valueOf(inputScanner.nextFloat());
            System.out.println();
        }
        if(promptType == UserPromptTypes.Double){
            System.out.print("double::" + prompt);
            returns = String.valueOf(inputScanner.nextDouble());
            System.out.println();
        }
        if(promptType == UserPromptTypes.String){
            System.out.print("string::" + prompt);
            returns = inputScanner.nextLine();
            System.out.println();
        }
        //TODO: etc, etc (userPrompt tempalte types)
        return returns;
    }

}
