package com.umidity.cli.frames;
import com.umidity.Debugger;
import com.umidity.cli.frames.forms.Form;
import com.umidity.cli.frames.forms.formEvents.Callable;

import java.util.*;

public class Frame {
    Vector<Form> forms;
    private String name;


    public Frame(){
        this.forms = new Vector<>();
    }

    public void show(){
        for(Form f:forms){
            f.show();
            Callable tocall = f.nextEvent();
            if(tocall != null)
                invoke(tocall);
        }
    }

    private static void invoke(Callable callable){
        try{
            callable.call();
        }catch (InterruptedException e){
            Debugger.println("Event could not be executed!" + e.getMessage() + " " + e.getCause());
        }
    }

    public Frame addForm(Form newForm){ forms.add(newForm); return this; }
    public Frame addForms(Collection<Form> forms){ forms.addAll(forms); return this; }

    public Vector<Form> getForms(){ return forms; }
    public <T> T getForm(String formName){
        for(Form f:forms){
            if(f.getName() == formName) return (T)f;
        }
        return null;
    }


    public String getName() { return name; }
    public Frame setName(String name) { this.name = name; return this; }
}
