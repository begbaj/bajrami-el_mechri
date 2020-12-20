package umidity.cli.forms.prompt;

import umidity.cli.forms.Form;

import java.util.Scanner;

public class UserPrompt implements Form {

    protected String name;
    protected Object content;
    protected boolean isVisible;
    protected Scanner inputScanner;

    protected  Prompt prompt;

    public UserPrompt(Prompt prompt){
        this.prompt = prompt;
        initScanner();
    }

    public void initScanner(){
        if(inputScanner == null)
            inputScanner = new Scanner(System.in);
        else {
            inputScanner.close();
            inputScanner = new Scanner(System.in);
        }
    }

    public Prompt getPrompt(){return prompt;}
    @Override
    public void show() {
        if(isVisible){
            System.out.print(prompt);
        }
    }
    @Override
    public void setVisibility(boolean value) { isVisible = value; }
    @Override
    public void setName(String name) { this.name = name; }
    @Override
    public String getName() { return name; }
    @Override
    public <T> T setContent(T value) {
        return null;
    }
    @Override
    public <T> T getContent() {
        return null;
    }

    public String getUserInput(UserPromptTypes type){
        String returns = "";
        prompt.type = type;
        show();
        if(type == UserPromptTypes.Integer){
            returns += String.valueOf(inputScanner.nextInt());
        }
        if(type == UserPromptTypes.Float){
            returns = String.valueOf(inputScanner.nextFloat());
        }
        if(type == UserPromptTypes.Double){
            returns = String.valueOf(inputScanner.nextDouble());
        }
        if(type == UserPromptTypes.String){
            return inputScanner.nextLine();

        }
        //Poichè diversamente dà errore
        try{
            inputScanner.nextLine(); //TODO: Trovare metodo alternativo
        }catch (Exception e){

        }
        System.out.println();
        //TODO: etc, etc (userPrompt tempalte types)
        return returns;
    }

}
