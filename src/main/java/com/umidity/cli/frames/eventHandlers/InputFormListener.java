package com.umidity.cli.frames.eventHandlers;

public interface InputFormListener {
//    void onKeyStroke(Object obj, InputFormArgument arg);
//    void onDelete(Object obj, InputFormArgument arg);
//    void isEmpty(Object obj, InputFormArgument arg);

    /**
     * when input is submitted
     * @param obj
     * @param arg
     */
    public void onSubmit(Object obj, InputFormArgument arg);

    /**
     * when a not valid character is found
     * @param obj
     * @param arg
     */
    public void onNotValidCharacter(Object obj, InputFormArgument arg);
}
