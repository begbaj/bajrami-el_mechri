package umidity.cli;

import umidity.cli.forms.FormManager;
import umidity.cli.forms.message.MenuBox;
import umidity.cli.forms.message.MessageBox;
import umidity.cli.forms.prompt.Prompt;
import umidity.cli.forms.prompt.UserPrompt;
import umidity.cli.forms.prompt.UserPromptTypes;

public class MainMenu extends FormManager {

    @Override
    protected void beforeUpdate(){
        if(path == null) startupMenu();
        switch (path){
            case "startupMenu" -> startupMenu();
            case "currentWeatherMenu" -> currentWeatherMenu();
            case "forecastWeatherMenu" -> forecastWeatherMenu();
            case "forecastByCityName" -> forecastByCityName();
            case "forecastByCityId" -> forecastByCityId();
            case "forecastByZipCode" -> forecastByZipCode();
            case "forecastByCoordinates" -> forecastByCoordinates();
            case "currentByCityName" -> currentByCityName();
            case "currentByCityId" -> currentByCityId();
            case "currentByZipCode" -> currentByZipCode();
            case "currentByCoordinates" -> currentByCoordinates();
            default -> startupMenu();
        }

    }

    @Override
    protected void init() {
        setClearScreen(false);
        MenuBox menu = new MenuBox();
        menu.setName("menu");
        menu.setVisibility(true);
        addForm(menu);

        MessageBox messages = new MessageBox();
        messages.setName("messages");
        messages.setVisibility(true);
        addForm(messages);

        UserPrompt userPrompt = new UserPrompt(new Prompt("MainMenu>"));
        userPrompt.setVisibility(true);
        userPrompt.setName("input");
        addForm(userPrompt);

        navigate("startupMenu");
    }

    @Override
    public void afterUpdate(){
        if(inputRequired)
            input = ((UserPrompt)getForm("input")).getUserInput(inputType);
    }


    private void startupMenu(){
        if(input != null){
            switch (consumeInput()){
                case "1" -> navigate("currentWeatherMenu", true);
                case "2" -> navigate("forecastWeatherMenu", true);
                case "0" -> quit();
            }
        }

        MenuBox menu = (MenuBox)getForm("menu");
        menu.clear();
        menu.setVisibility(true);
        menu.add("Richiedi informazioni meteo correnti");
        menu.add("Richiedi previsioni meteo");
        menu.add("Chiudi");
        menu.refresh(1);
        inputRequired = true;
        inputType = UserPromptTypes.Integer;
    }
    private void currentWeatherMenu(){
        if(input != null){
            switch (consumeInput()){
                case "1" -> navigate("currentByCityName", true);
                case "2" -> navigate("currentByCoordinates", true);
                case "3" -> navigate("currentByZipCode", true);
                case "4" -> navigate("currentByCityId", true);
                case "0" -> navigate("startupMenu", true);
            }
        }

        MenuBox menu = (MenuBox)getForm("menu");
        menu.clear();
        menu.setVisibility(true);
        menu.add("Nome città");
        menu.add("Coordinate");
        menu.add("Codice postale");
        menu.add("Id città (id preso da openweather)");
        //menu.add("Visualizza lista id delle città disponibili");
        menu.add("Chiudi");
        menu.refresh(1);
        inputRequired = true;
        inputType = UserPromptTypes.Integer;

    }
    private void forecastWeatherMenu(){
        if(input != null){
            switch (consumeInput()){
                case "1" -> navigate("forecastByCityName", true);
                case "2" -> navigate("forecastByCoordinates", true);
                case "3" -> navigate("forecastByZipCode", true);
                case "4" -> navigate("forecastByCityId", true);
                case "0" -> navigate("startupMenu", true);
            }
        }

        MenuBox menu = (MenuBox)getForm("menu");
        menu.clear();
        menu.setVisibility(true);
        menu.add("Nome città");
        menu.add("Coordinate");
        menu.add("Codice postale");
        menu.add("Id città (id preso da openweather)");
        //menu.add("Visualizza lista id delle città disponibili");
        menu.add("Chiudi");
        menu.refresh(1);
        inputRequired = true;
        inputType = UserPromptTypes.Integer;
    }

    public void forecastByCityName(){

    }

    public void forecastByCityId(){

    }

    public void forecastByZipCode(){

    }

    public void forecastByCoordinates(){

    }

    public void currentByCityName(){

    }

    public void currentByCityId(){

    }

    public void currentByZipCode(){

    }

    public void currentByCoordinates(){

    }

}

