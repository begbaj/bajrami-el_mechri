package com.umidity.cli.frames;

import com.umidity.Debugger;

import java.util.Vector;

/**
 * <p>
 * FrameManager is the main character on Form management for the CLI.
 * Best practice suggests to extend this class in order to override "refresh methods".
 * </p>
 *
 * <p>
 * "Refresh methods" are those executed every "frame update" to perform update actions; more specifically:
 * 1 - init(): executed only the first time <em>refresh()</em> is called. it's suitable for initialization actions.
 * 2 - beforeUpdate(): actions executed before frame update. Suitable for changing the view.
 * 3 - update(): during frame update. Override this to update the view.
 * 4 - afterUpdate(): actions performed after frame update. Suitable for input management.
 * </p>
 *
 */
public class FrameManager {

    protected Vector<Frame> frames = new Vector<>();
    protected Boolean inited = false;
    protected String path;


    /**
     * Add a Frame to the Frame list
     * @param frame
     */
    public void add(Frame frame){ frames.add(frame); }
    /**
     * Remove a Frame from the Frame list.
     *
     * Usage example: frameManager.remove(frameManager.getFrame("SettingsFrame"));
     * @param frame
     */
    public void remove(Frame frame){ frames.remove(frame); }


    /**
     * Get a Frame in the Frame list which has the given name.
     * @param name name assigned to the Frame at initialization
     * @return
     */
    public Frame getFrame(String name){
        for(Frame ff:frames){
            if(ff.getName().equals(name)) return ff;
        }
        return null;
    }

    /**
     * Get first frame in the Frame list
     * @return
     */
    public Frame getFrame(){
        if(!frames.isEmpty())
            return frames.firstElement();
        return null;
    }

    /**
     * Refresh view
     */
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

    /**
     * For every frame on this FrameManager, call <em>show()</em> method, which will call every <em>show()</em> method
     * on every Form enabled in the Frame.
     */
    protected void refreshScreen(){
        for(Frame ff:frames){
            ff.show();
        }
    }

    /**
     * Clear console view
     *
     * Some code is directly taken from StackOverflow, needs revision.
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

    /**
     * set a new path for next screen update
     * @param newPath
     */
    public void setPath(String newPath){
        path = newPath;
    }

}
