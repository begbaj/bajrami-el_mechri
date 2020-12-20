package umidity.cli.forms;

import umidity.Debugger;
import umidity.cli.forms.prompt.UserPrompt;
import umidity.cli.forms.prompt.UserPromptTypes;

import java.util.*;

public abstract class FormManager implements Runnable, Form {

    protected boolean inputRequired;
    protected UserPromptTypes inputType;
    protected boolean skipFrame = false;

    /**
     * Refresh screen every declared milliseconds
     */
    protected long refreshRate;
    /**
     * if true, the thread stops
     */
    protected boolean quit = false;
    protected boolean inited = false;
    protected boolean clearScreen = true;
    protected String input = null;
    protected List<Form> forms = new ArrayList<>();
    protected String path;

    boolean isVisible = false;
    String name = "form";
    Object content = new Object();

    @Override
    public void show() {
        if(!inited) __init();
        __beforeUpdate();
        __update();
        __afterUpdate();
    }

    public boolean getInputRequired(){return inputRequired;}
    @Override
    public void setVisibility(boolean value) { isVisible = value; }
    @Override
    public void setName(String name) { this.name = name; }
    @Override
    public String getName() { return name; }
    @Override
    public <T> T setContent(T value) { content = value; return value;}
    @Override
    public <T> T getContent() { return (T)content; }

    public void setClearScreen(boolean value){ clearScreen = value; }

    public List<Form> getForms(){ return forms; }

    public void run(){
        while(!quit){
            show();
            try {
                Thread.sleep(refreshRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void navigate(String to){
        path = to;
        inputRequired = false;
        input = null;
        skipFrame = false;
    }
    protected void navigate(String to, boolean skipFrame){
        path = to;
        inputRequired = false;
        input = null;
        skipFrame = skipFrame;
    }

    /**
     * Override this only IF you know what you're doing
     */
    protected void __init(){
        inited = true;
        init();
    }
    /**
     * Override this only IF you know what you're doing
     */
    protected void __beforeUpdate(){
        if(clearScreen)
            clearConsole();
        beforeUpdate();
    }
    /**
     * Override this only IF you know what you're doing
     */
    protected void __update(){
        if(!skipFrame){
            refreshScreen();
        update();
        }
        skipFrame = false;
    }
    /**
     * Override this only IF you know what you're doing
     */
    protected void __afterUpdate(){
        if(!skipFrame)
            afterUpdate();
        skipFrame = false;
    }

    /**
     * Override this to perform actions one time and never again
     */
    protected void init(){}
    /**
     * override this method to perform actions before the screen update
     */
    protected void beforeUpdate(){}
    /**
     * override this method to perform actions during the screen update
     */
    protected void update(){}
    /**
     * override this method to perform actions after the screen update
     */
    protected void afterUpdate(){}

    /**
     * Closes the FormManager instance (Stops the thread)
     */
    protected void quit(){ quit = true; }

    protected void refreshScreen(){
        for(Form f:forms){
            if(f.getClass() != UserPrompt.class)f.show(); //TODO: da creare interfaccia piuttosto che cosi
        }
    }

    public String consumeInput(){
        if(input != null){
            inputRequired = false;
            String temp = input;
            input = null;
            return temp;
        }
        return null;
    }

    public void addForm(Form newForm){ forms.add(newForm); }
    public void addForms(Collection<Form> forms){ forms.addAll(forms); }

    public long getRefreshRate(){ return refreshRate; }
    public void setRefreshRate(long value){ refreshRate = value;};

    public Form getForm(String formName){
        for(Form f:forms){
            if(f.getName() == formName) return f;
        }
        return null;
    }

    /**
     * Clear console
     */
    protected static void clearConsole() {
        try {
            Debugger.println("CLEARED");
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else { //nel caso in cui ci troviamo su linux o macOS: non è stato testato!
                System.out.print("\033[H\033[2J");
            }
            System.out.flush();
        } catch (final Exception e) {
            Debugger.println("something went wrong :(");
            e.printStackTrace();
        }
    }

}
