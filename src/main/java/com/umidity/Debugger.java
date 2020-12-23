package com.umidity;

public class Debugger {
    static boolean isActive = false;

    public static void setActive(boolean set){isActive = set;}

    public static void println(String message){
        if(isActive) System.out.println("Debugger:"+message);
    }

    public static void println(String message, Object obj){
        if(isActive){
            System.out.println("Debugger:"+message);
            System.out.println(obj.getClass().getName());
        }
    }


}
