package umidity.cli;

import umidity.api.ApiCaller;
import umidity.api.EMode;
import umidity.api.EUnits;
import umidity.api.response.ApiResponse;
import umidity.cli.forms.FormManager;
import umidity.cli.forms.message.MessageBox;
import umidity.cli.forms.message.MenuBox;
import umidity.cli.forms.prompt.Prompt;
import umidity.cli.forms.prompt.UserPrompt;
import umidity.cli.forms.prompt.UserPromptTypes;

import java.io.IOException;
import java.util.Vector;

public class MainCli extends FormManager {
    protected ApiCaller caller = new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EMode.JSON, EUnits.Metric);
    //TODO: prendere appid da un file di configurazione
//    private StringBuilder path = new StringBuilder();
    private String path = "";
    private boolean inputRequired = false;

    private ApiResponse response = new ApiResponse();
    private Vector<String> inputStorage = new Vector<>();
    private UserPromptTypes inputType;



    @Override
    protected void init(){
        MessageBox titleBox = new MessageBox();
        MessageBox contentBox = new MessageBox();
        MenuBox menuBox = new MenuBox();
        UserPrompt userPrompt = new UserPrompt(new Prompt(">"));

        titleBox.setVisibility(true);
        titleBox.setName("titleBox");
        titleBox.setContent("Umidity 0.1");

        contentBox.setVisibility(true);
        contentBox.setName("contentBox");
        contentBox.setContent("Welcome to Umidity!");

        menuBox.setVisibility(false);
        menuBox.setName("menuBox");
        menuBox.setContent("");

        userPrompt.setName("userPrompt");
        userPrompt.setVisibility(false);

        addForm(titleBox);
        addForm(contentBox);
        addForm(menuBox);
        addForm(userPrompt);
        __update();
        navigate("currentMenu");
    }

    @Override
    protected void beforeUpdate(){
        switch (path) {
            case "currentMenu" -> currentMenu();
            case "getByCityName" -> getByCityName();
            case "getByCoordinates" -> getByCoordinates();
            case "getByCityId" -> getByCityName();
            case "viewResponse" -> viewResponse();
            case "quit" -> quit();
        }

    }

    @Override
    protected void update(){

    }

    @Override
    protected void afterUpdate(){
        if(inputRequired){
            if(input == null){
                ((UserPrompt)getForm("userPrompt")).setVisibility(true);
                input = ((UserPrompt)getForm("userPrompt")).getUserInput(inputType);
            }
        }else{
            input = null;
            ((UserPrompt)getForm("userPrompt")).setVisibility(false);
        }
    }

    private void navigate(String to){
        path = to;
        inputRequired = false;
        input = null;
        inputStorage = new Vector<>();
    }

    private void navigate(String to, boolean clearInput){
        path = to;
        inputRequired = false;
        if(clearInput)
        {
            input = null;
            inputStorage = new Vector<>();
        }
    }

    private int currentMenu(){
        inputRequired = true;
        inputType = UserPromptTypes.Integer;

        getForm("contentBox").setVisibility(false);
        MenuBox menu = (MenuBox) getForm("menuBox");

        menu.clear();
        menu.add("to quit");
        menu.add("Get by city name");
        menu.add("Get by city coordinates");
        menu.add("Get by city id");
        menu.refresh(true);
        menu.setVisibility(true);


        if(input != null) {
            switch (input) {
                case "1" -> navigate("getByCityName");
                case "2" -> navigate("getByCoordinates");
                case "3" -> navigate("getByCityId");
                case "0" -> navigate("quit");
            }
            menu.clear();
            menu.setVisibility(false);
        }
        return 1;
    }
    private int getByCityName(){
        inputRequired = true;
        inputType = UserPromptTypes.String;

        MessageBox contentBox = (MessageBox) getForm("contentBox");
        contentBox.setVisibility(true);
        contentBox.setContent("Inserire nome città:");

        if(input != null){
            try {
                response = caller.getByCityName(input, "","");
                navigate("viewResponse");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
    private int getByCoordinates(){
        inputRequired = true;
        inputType = UserPromptTypes.String;

        MessageBox contentBox = (MessageBox) getForm("contentBox");
        contentBox.setVisibility(true);

        String content = "";
        content += "Latitudine: " + ( inputStorage.size() > 0 ? inputStorage.elementAt(0) : "?")
                + " Longitudine: " + ( inputStorage.size() > 1 ? inputStorage.elementAt(1) : "?")
                + "\n";

        if(inputStorage.size() == 0)
            content += "Inserire latitudine:";
        else{
            content += "Inserire Longitudine:";
        }

        if(input != null){
            inputStorage.add(input);
            input = null;
        }

        contentBox.setContent(content);
        if(inputStorage.size() > 2){
            try {
                response = caller.getByCoordinates(
                        Float.parseFloat(inputStorage.elementAt(0)),
                        Float.parseFloat(inputStorage.elementAt(1)));
                navigate("viewResponse");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }


    private int viewResponse(){
        inputRequired = true;
        inputType = UserPromptTypes.String;

        MessageBox contentBox = (MessageBox) getForm("contentBox");
        contentBox.setVisibility(true);

        String content = "";

        content += "Città: " + response.name + "\n"
         + "Umidità attuale: " + response.main.humidity + "\n"
         + "Temperatura attuale: " + response.main.temp + "\n"
         + "Temperatura massima: " + response.main.temp_max + "\n"
         + "Temperatura minima: " + response.main.temp_min + "\n"
         + "Inserire (q) per tornare al menu principale";

        contentBox.setContent(content);


        if(input != null){
            if(input.equals("q")){
                contentBox.setVisibility(false);
                navigate("mainMenu");
            }
            else{
                input = null;
            }
        }

        return 1;
    }

}
