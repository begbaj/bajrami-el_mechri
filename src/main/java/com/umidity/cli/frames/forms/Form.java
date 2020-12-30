package com.umidity.cli.frames.forms;

import com.umidity.cli.frames.forms.formEvents.Callable;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Form generalized class.
 */
public abstract class Form {
    boolean enabled = true;
    boolean isVisible = false;
    protected Queue<Callable> events = new ArrayDeque<>();

    String name = "";


    public void show(){
        if(!enabled && !isVisible) return;
    }

    public void addEvent(Callable newEvent){
        events.add(newEvent);
    }

    public void enable(){ enabled = true; }
    public void disable(){ enabled = false; }
    public boolean getEnabled(){ return enabled; }
    public Callable nextEvent(){ return events.poll();}

    /**
     * if true, when show is called, the Form will be showed to the user
     * @param value
     */
    public Form setVisibility(boolean value){isVisible = value; return this;};
    public Form setName(String name){this.name = name; return this;};
    public boolean getVisibility(){return isVisible;}
    public String getName(){return name;};
}
