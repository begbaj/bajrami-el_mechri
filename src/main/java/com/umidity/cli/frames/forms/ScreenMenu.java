package com.umidity.cli.frames.forms;

import com.umidity.Pair;

import java.util.HashMap;
import java.util.Vector;

public class ScreenMenu extends ScreenText {

    private HashMap<Integer, Pair<String, String>> menu = new HashMap<>();
    private String menuText;
    private Vector<String> history = new Vector<>();

    public void addMenuEntry(String entry, String value){
        menu.put(menu.size(), new Pair<>(entry, value));
    }

    public String[] getHistory(){
        return (String[])history.toArray();
    }

    public String getHistory(int index){
        return history.elementAt(index);
    }

    public void clearHistory(){
        history.clear();
    }

    public void addHistory(String add){
        history.add(add);
    }

    @Override
    public void show(){
        super.show();
        if(enabled && isVisible) System.out.println(menuText);
    }

    public void updateMenu(){
        menuText = "";
        for(int i=0; i<menu.size(); i++){
            menuText += i + ") " + menu.get(i).getKey() + "\n";
        }
    }

    public void clearMenuEntries(){
        menu.clear();
    }

    public String getValue(Integer index){
        return menu.get(index).getValue();
    }

}
