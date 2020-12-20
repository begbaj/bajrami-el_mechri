package umidity.cli;

import umidity.api.ApiCaller;
import umidity.api.EMode;
import umidity.api.EUnits;
import umidity.api.response.ApiResponse;
import umidity.cli.forms.Form;
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
    private UserPromptTypes inputType;
    private boolean inputRequired = false;



    @Override
    protected void init(){
        MessageBox titleBox = new MessageBox();
        titleBox.setVisibility(true);
        titleBox.setName("titleBox");
        titleBox.setContent("Umidity 0.1");

        MessageBox warningViewer = new MessageBox();
        warningViewer.setVisibility(true);
        warningViewer.setName("warningViewer");
        warningViewer.setContent("Welcome to Umidity!");

        MainMenu mainMenu = new MainMenu();
        mainMenu.setName("mainMenu");
        mainMenu.setVisibility(false);

        ResponseViewer responseViewer = new ResponseViewer();
        responseViewer.setName("responseViewer");
        responseViewer.setVisibility(false);

        UserPrompt mainPrompt = new UserPrompt(new Prompt(">"));
        mainPrompt.setName("mainPrompt");
        mainPrompt.setVisibility(false);


        addForm(titleBox);
        addForm(warningViewer);
        addForm(mainMenu);
        addForm(responseViewer);
        addForm(mainPrompt);

        navigate("mainMenu");
    }

    @Override
    protected void beforeUpdate(){
        switch (path) {
            case "mainMenu" -> setVisibleUnique("titleBox,mainMenu");
            case "responseViewer" -> setVisibleUnique("titleBox,responseViewer");
        }

    }

    @Override
    protected void afterUpdate(){
        UserPrompt mainPrompt = (UserPrompt) getForm("mainPrompt");
        if(inputRequired){
            if(input == null){
                mainPrompt.setVisibility(true);
                input = mainPrompt.getUserInput(inputType);
            }
        }else{
            input = null;
            mainPrompt.setVisibility(false);
        }
    }

    /**
     * comma separated
     * @param formNames
     */
    private void setVisibleUnique(String formNames){
        String[] separated = formNames.split(",");
        for(Form f:this.forms){
            for(String s:separated){
                if(f.getName().equals(s)){
                    f.setVisibility(true);
                    break;
                }
                else f.setVisibility(false);
            }
        }
    }

    //
//    private void navigate(String to, boolean clearInput){
//        path = to;
//        inputRequired = false;
//        if(clearInput)
//        {
//            input = null;
//            inputStorage = new Vector<>();
//        }
//    }
//
//    private int currentMenu(){
//        inputRequired = true;
//        inputType = UserPromptTypes.Integer;
//
//        getForm("contentBox").setVisibility(false);
//        MenuBox menu = (MenuBox) getForm("menuBox");
//
//        menu.clear();
//        menu.add("Get by city name");
//        menu.add("Get by city coordinates");
//        menu.add("Get by city id");
//        menu.add("To quit");
//        menu.refresh(1);
//        menu.setVisibility(true);
//
//
//        if(input != null) {
//            switch (input) {
//                case "1" -> navigate("getByCityName");
//                case "2" -> navigate("getByCoordinates");
//                case "3" -> navigate("getByCityId");
//                case "0" -> navigate("quit");
//            }
//            menu.clear();
//            menu.setVisibility(false);
//        }
//        return 1;
//    }
//    private int getByCityName(){
//        inputRequired = true;
//        inputType = UserPromptTypes.String;
//
//        MessageBox contentBox = (MessageBox) getForm("contentBox");
//        contentBox.setVisibility(true);
//        contentBox.setContent("Inserire nome città:");
//
//        if(input != null){
//            try {
//                response = caller.getByCityName(input, "","");
//                navigate("viewResponse");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return 0;
//    }
//    private int getByCoordinates(){
//        inputRequired = true;
//        inputType = UserPromptTypes.String;
//
//        MessageBox contentBox = (MessageBox) getForm("contentBox");
//        contentBox.setVisibility(true);
//
//        String content = "";
//        content += "Latitudine: " + ( inputStorage.size() > 0 ? inputStorage.elementAt(0) : "?")
//                + " Longitudine: " + ( inputStorage.size() > 1 ? inputStorage.elementAt(1) : "?")
//                + "\n";
//
//        if(inputStorage.size() == 0)
//            content += "Inserire latitudine:";
//        else{
//            content += "Inserire Longitudine:";
//        }
//
//        if(input != null){
//            inputStorage.add(input);
//            input = null;
//        }
//
//        contentBox.setContent(content);
//        if(inputStorage.size() > 2){
//            try {
//                response = caller.getByCoordinates(
//                        Float.parseFloat(inputStorage.elementAt(0)),
//                        Float.parseFloat(inputStorage.elementAt(1)));
//                navigate("viewResponse");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return 0;
//    }
//
//
//    private int viewResponse(){
//        inputRequired = true;
//        inputType = UserPromptTypes.String;
//
//        MessageBox contentBox = (MessageBox) getForm("contentBox");
//        contentBox.setVisibility(true);
//
//        String content = "";
//
//        content += "Città: " + response.name + "\n"
//         + "Umidità attuale: " + response.main.humidity + "\n"
//         + "Temperatura attuale: " + response.main.temp + "\n"
//         + "Temperatura massima: " + response.main.temp_max + "\n"
//         + "Temperatura minima: " + response.main.temp_min + "\n"
//         + "Inserire (q) per tornare al menu principale";
//
//        contentBox.setContent(content);
//
//
//        if(input != null){
//            if(input.equals("q")){
//                contentBox.setVisibility(false);
//                navigate("mainMenu");
//            }
//            else{
//                input = null;
//            }
//        }
//
//        return 1;
//    }


}
