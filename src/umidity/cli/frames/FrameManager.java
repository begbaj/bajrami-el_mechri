package umidity.cli.frames;

import umidity.Debugger;

import java.util.Vector;

public class FrameManager {
    protected Vector<Frame> frames = new Vector<>();
    protected Boolean inited = false;

    public void add(Frame frame){ frames.add(frame); }
    public void remove(Frame frame){ frames.remove(frame); }
    public Frame getFrame(String name){
        for(Frame ff:frames){
            if(ff.getName().equals(name)) return ff;
        }
        return null;
    }
    public Frame getFrame(){
        if(!frames.isEmpty())
            return frames.firstElement();
        return null;
    }

    public void refresh() {
        if(!inited) __init();
        __beforeUpdate();
        __update();
        __afterUpdate();
    }

    /**
     * Override this only IF you know what you're doing
     */
    protected void __init(){ init(); inited = true; }
    /**
     * Override this only IF you know what you're doing
     */
    protected void __beforeUpdate(){ clearConsole(); beforeUpdate(); }
    /**
     * Override this only IF you know what you're doing
     */
    protected void __update(){ refreshScreen(); update(); }
    /**
     * Override this only IF you know what you're doing
     */
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

    protected void refreshScreen(){
        for(Frame ff:frames){
            ff.show();
        }
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
