package com.umidity.cli.frames;

import com.umidity.Debugger;
import com.umidity.cli.frames.forms.Form;
import com.umidity.cli.frames.forms.formEvents.Callable;

import java.util.Collection;
import java.util.Vector;

public class Frame {
    Vector<Form> forms;
    private String name;


    public Frame(){
        this.forms = new Vector<>();
    }

    /**
     * Show to screen each form in the list
     */
    public void show(){
        for(Form f:forms){
            f.show();
            Callable tocall = f.nextEvent();
            if(tocall != null)
                invoke(tocall);
        }
    }

    /**
     * Used to invoke events on each Form
     * @param callable
     */
    private static void invoke(Callable callable){
        try{
            callable.call();
        }catch (InterruptedException e){
            Debugger.println("Event could not be executed!" + e.getMessage() + " " + e.getCause());
        }
    }

    /**
     * Add a new Form to the Form list
     * @param newForm
     * @return
     */
    public Frame addForm(Form newForm){ forms.add(newForm); return this; }

    /**
     * Add a list of Forms in the Form list
     * @param forms
     * @return
     */
    public Frame addForms(Collection<Form> forms){ forms.addAll(forms); return this; }

    /**
     * Get the list of forms in this frame
     * @return
     */
    public Vector<Form> getForms(){ return forms; }

    /**
     * Gets a single Form by the given name
     *
     * Usage Example: ((InputForm)frame.getForm("userInput"))
     * @param formName name of the form to search for
     * @param <T> Cast type for the Form
     * @return
     */
    public <T> T getForm(String formName){
        for(Form f:forms){
            if(f.getName() == formName) return (T)f;
        }
        return null;
    }


    /**
     * Gets this Frame name
     * @return this frame's name
     */
    public String getName() { return name; }

    /**
     * Sets this frame name
     * @param name new name
     * @return
     */
    public Frame setName(String name) { this.name = name; return this; }
}
