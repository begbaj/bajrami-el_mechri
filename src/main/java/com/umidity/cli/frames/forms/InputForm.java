package com.umidity.cli.frames.forms;

import com.umidity.cli.frames.eventHandlers.*;

import java.util.ArrayList;

public abstract class InputForm extends Form {

    protected boolean isPassword;
    protected String text;
    protected boolean deafen;
    protected boolean close;
    protected boolean inited;

    public Form setText(String text){this.text = text; return this;}

    protected ArrayList<InputFormListener> inputListeners = new ArrayList<>();

    public void setInputListeners(InputFormListener inputListener){
        this.inputListeners.add(inputListener);
    }


    public void show(){
        if(!isPassword)
            super.show();
    }

//    public void open(){
//        deafen = false;
//        close = false;
//    }
//
//    public void listen(){
//        StringBuilder input = new StringBuilder("");
//        String c = "nan";
//        while(!input.toString().equals("\n") || !close){
//            if(!deafen){
//                try{
//                    c = String.valueOf((char)System.in.readNBytes(1)[0]); // richiedere input in un altro thread?
//                    if(inputListener != null){
//                        inputListener.onKeyStroke(this, new InputFormArgument(c));
//                        if(c.equals("\n"))
//                            inputListener.onSubmit(this, new InputFormArgument());
//                        if(c.equals((char)127)){
//                            inputListener.onDelete(this, new InputFormArgument());
//                            input.deleteCharAt(input.length()-1);
//                            continue;
//                        }
//                    }
//                    input.append(c);
//                }catch (IOException e){
//                    if(inputListener != null)
//                        inputListener.onNotValidCharacter(this, new InputFormArgument(c));
//                }
//            }
//        }
//
//    }
//
//    public void deafen(){
//        deafen=true;
//    }
//
//    public void close(){
//        close = true;
//    }
}
