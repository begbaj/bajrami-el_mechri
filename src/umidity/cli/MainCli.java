package umidity.cli;

import umidity.Main;
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
import java.util.Scanner;
import java.util.Vector;

public class MainCli extends FormManager {
    protected ApiCaller caller = new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EMode.JSON, EUnits.Metric);
    //TODO: prendere appid da un file di configurazione
//    private StringBuilder path = new StringBuilder();
    private String path = "";
    private boolean inputRequired = false;
    private UserPromptTypes type;
    private ApiResponse response = new ApiResponse();



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
        navigate("mainMenu");
    }

    @Override
    protected void beforeUpdate(){
        if(path.equals("mainMenu")) mainMenu();
        else if(path.equals("getByCityName")) getByCityName();
        else if(path.equals("viewResponse")) viewResponse();
        else if(path.equals("quit")) quit();

//        if(path.toString() == ""){
//            //
//        }else{
//            if(paths[0].equals("selected")){
//                if(paths[1].equals("mainMenu")){
//                    if(paths.length > 2 && paths[2].equals("getByCityName")){
//                        if(paths.length > 3 && paths[3].equals("viewResponse"))
//                            viewResponse();
//                        getByCityName();
//                        return;
//                    }
//                    mainMenu();
//                    return;
//                }
//                return;
//            }
//            return;
//        }
    }

    @Override
    protected void update(){

    }

    @Override
    protected void afterUpdate(){
        if(inputRequired){
            if(input == null){
                ((UserPrompt)getForm("userPrompt")).setVisibility(true);
                input = ((UserPrompt)getForm("userPrompt")).getUserInput(type);
            }
        }else{
            input = null;
            ((UserPrompt)getForm("userPrompt")).setVisibility(false);
        }
    }

    private void navigate(String to){
//        for(String t:to.split("/"))
//        if(t == ".."){
//            int i = path.lastIndexOf("/");
//            if(i >= 0)
//                this.path.delete(i, path.length()-1);
//            else{
//                if(path.length() > 0)
//                    this.path.delete(0, path.length());
//            }
//        }else{
//            this.path.append(t + "/");
//            getForm("titleBox").setContent(t);
//        }
//        inputRequired = false;
//        input=null;
        path = to;
        input = null;
        inputRequired = false;
    }

    private int mainMenu(){
        getForm("contentBox").setVisibility(false);
        MenuBox menu = (MenuBox) getForm("menuBox");

        menu.clear();
        menu.add("to quit");
        menu.add("Get by city name");
        menu.add("Get by city coordinates");
        menu.add("Get by city id");
        menu.refresh(true);
        menu.setVisibility(true);

        inputRequired = true;
        type = UserPromptTypes.Integer;
        if(input != null) {
            if(input.equals("1")) navigate("getByCityName");
            else if(input.equals("0")) navigate("quit");
            menu.clear();
            menu.setVisibility(false);
        }
        return 1;
    }
    private int getByCityName(){
        MessageBox contentBox = (MessageBox) getForm("contentBox");
        contentBox.setVisibility(true);
        contentBox.setContent("Inserire nome città:");
        inputRequired = true;
        type = UserPromptTypes.String;
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
    private int viewResponse(){
        MessageBox contentBox = (MessageBox) getForm("contentBox");
        contentBox.setVisibility(true);
        String content = "";
        content += "Umidità attuale: " + response.main.humidity + "\n";
        content += "Temperatura attuale: " + response.main.temp + "\n";
        content += "Temperatura massima: " + response.main.temp_max + "\n";
        content += "Temperatura minima: " + response.main.temp_min + "\n";
        content += "Inserire (q) per tornare al menu principale";
        contentBox.setContent(content);
        inputRequired = true;
        type = UserPromptTypes.String;
        if(input != null){
            if(input.equals("q")){
                contentBox.setVisibility(false);
                navigate("mainMenu");
            }
        }
        return 1;
    }

}
