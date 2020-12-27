package com.umidity;

/**
 * A debugging class, used to print to System.out custom messages
 */
public class Debugger {
    /**
     * when active, Debugger will print debugging messages to System.out
     */
    static boolean isActive = false;

    /**
     * Activate(true)/Deactivate(false) debugging messages.
     * @param set
     */
    public static void setActive(boolean set){isActive = set;}

    /**
     * Print debugging message.
     * @param message
     */
    public static void println(String message){
        if(isActive) System.out.println("Debugger:"+message);
    }
    /**
     * Print debugging message.
     * @param message
     */
    public static void println(String message, Object obj){
        if(isActive){
            System.out.println("Debugger:"+message);
            System.out.println(obj.getClass().getName());
        }
    }


}
