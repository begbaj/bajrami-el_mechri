package umidity.cli.forms;

import umidity.Debugger;
import umidity.cli.forms.prompt.UserPrompt;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public abstract class FormManager implements Runnable {

    /**
     * Refresh screen every declared milliseconds
     */
    protected long refreshRate;
    /**
     * if true, the thread stops
     */
    protected boolean quit = false;
    protected boolean inited = false;
    protected String input = null;
    protected List<Form> forms = new ArrayList<>();



    public void run(){
        if(!inited) __init();
        while(!quit)
        {
            __beforeUpdate();
            __update();
            __afterUpdate();
            try {
                Thread.sleep(refreshRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Override this only IF you know what you're doing
     */
    protected void __init(){ inited = true; init();  }
    /**
     * Override this only IF you know what you're doing
     */
    protected void __beforeUpdate(){ clearConsole(); beforeUpdate(); }
    /**
     * Override this only IF you know what you're doing
     */
    protected void __update(){ refreshScreen(); update(); }
    protected void __afterUpdate(){ afterUpdate(); }

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

    public void addForm(Form newForm){ forms.add(newForm); }
    public void addForms(Collection<Form> forms){ forms.addAll(forms); }

    public long getRefreshRate(){ return refreshRate; }
    public void setRefreshRate(long value){ refreshRate = value;};

    public Form getForm(String formName)
    {
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
