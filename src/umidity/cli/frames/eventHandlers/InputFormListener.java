package umidity.cli.frames.eventHandlers;

public interface InputFormListener {
//    void onKeyStroke(Object obj, InputFormArgument arg);
//    void onDelete(Object obj, InputFormArgument arg);
//    void isEmpty(Object obj, InputFormArgument arg);
    public void onSubmit(Object obj, InputFormArgument arg);
    public void onNotValidCharacter(Object obj, InputFormArgument arg);
}
