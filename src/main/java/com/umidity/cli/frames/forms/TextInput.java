package com.umidity.cli.frames.forms;

import com.umidity.Debugger;

import com.umidity.cli.frames.eventHandlers.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumSet;

public class TextInput extends InputForm{
    protected String input;
    protected EnumSet<InputRestrictions> restrictions;
    protected boolean exclude;


    public int getLength(){
        if(input != null) return input.split(",,").length;
        return 0;
    }

    public boolean isInput(){
        if(input == null) return false;
        return true;
    }

    /**
     * Get stored input
     * @return
     */
    public String getInput(){ return input; }

    /**
     * Get stored input
     * @return
     */
    public String consumeInput(){
        String temp = input;
        input = null;
        return temp;
    }

    /**
     * Get stored input
     * @return
     */
    public String[] consumeInputs(){
        String[] temp = input.split(",,");
        input = null;
        return temp;
    }

    /**
     * Get splitted Inputs
     * @return
     */
    public String[] getInputs(){ return input.split(",,"); }

    /**
     * Set input restrictions
     * @param exclude
     * @param newRestricions
     */
    public void setRestrictions(boolean exclude, EnumSet<InputRestrictions> newRestricions){
        this.exclude = exclude;
        restrictions = newRestricions;
    }

    /**
     * print this form to screen and run needed methods
     */
    public void show(){
        super.show();
        System.out.print(text);
    }

    /**
     * Overwrite last input
     * @return
     */
    public String openStream(){
        if(!enabled) return null;
        input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            input = reader.readLine();
            for(var i:inputListeners) i.onSubmit(this, new InputFormArgument(input));
        } catch (IOException e) {
            Debugger.println(e.getMessage());
        }
        return input;
    }

    /**
     * Add to last input
     * @return
     */
    public String[] appendStream(){
        if(!enabled) return null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String typed = reader.readLine();
            typed = typed.equals("")? " " : typed;
            if(getLength() > 0)input += ",," + typed;
            else input = typed;
            for(var i:inputListeners) i.onSubmit(this, new InputFormArgument(input));
        } catch (IOException e) {
            Debugger.println(e.getMessage());
        }
        return input.split(",,");
    }

    /**
     * check input for requested restrictions
     * @param s
     * @return
     */
    protected boolean checkRestrictions(String s){
        //TODO: da implementare
//        boolean isValid = true;
//        if(restrictions.contains(InputRestrictions.alpha)){
//            isValid = s.matches("\\w");
//        }
        return false;
    }



}