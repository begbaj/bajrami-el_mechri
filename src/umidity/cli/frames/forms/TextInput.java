package umidity.cli.frames.forms;

import umidity.Debugger;

import umidity.cli.frames.eventHandlers.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumSet;

public class TextInput extends InputForm{
    protected String input;
    protected EnumSet<InputRestrictions> restrictions;
    protected boolean exclude;

    public String getInput(){ return input; }
    public String[] getInputs(){ return input.split(","); }

    public void setRestrictions(boolean exclude, EnumSet<InputRestrictions> newRestricions){
        this.exclude = exclude;
        restrictions = newRestricions;
    }

    public void show(){
        super.show();
        System.out.print(text);
    }

    public String openStream(){
        if(!enabled) return "";
        input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            input = reader.readLine();
            if(inputListener != null) inputListener.onSubmit(this, new InputFormArgument(input));
        } catch (IOException e) {
            Debugger.println(e.getMessage());
        }finally {
//            try {
//                reader.close();
//            } catch (IOException e) {
//                Debugger.println(e.getMessage());
//            }
        }
        return input;
    }

    public String[] appendStream(){
        input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            input += "," + reader.readLine();
            if(inputListener != null) inputListener.onSubmit(this, new InputFormArgument(input));
        } catch (IOException e) {
            Debugger.println(e.getMessage());
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                Debugger.println(e.getMessage());
            }
        }
        return input.split(",");
    }

    protected boolean checkRestrictions(String s){
        //TODO: da implementare
//        boolean isValid = true;
//        if(restrictions.contains(InputRestrictions.alpha)){
//            isValid = s.matches("\\w");
//        }
        return false;
    }



}