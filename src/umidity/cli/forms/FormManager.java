package umidity.cli.forms;

import umidity.Debugger;

public abstract class FormManager implements Form, Runnable {

    /**
     * Refresh screen every declared milliseconds
     */
    private long refreshRate;
    /**
     * if true, the thread stops
     */
    private boolean quit = false;


    public void run(){
        init();
        while(!quit)
        {
            beforeUpdate();
            update();
            afterUpdate();
            try {
                Thread.sleep(refreshRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    protected void init(){

    }
    protected void beforeUpdate(){ clearConsole(); }
    protected void update(){}
    protected void afterUpdate(){}
    protected void quit(){ quit = true; }


    public long getRefreshRate(){ return refreshRate; }
    public void setRefreshRate(long value){ refreshRate = value;};

    /**
     * Clear console
     */
    private static void clearConsole() {
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
