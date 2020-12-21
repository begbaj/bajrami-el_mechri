package umidity.cli.frames.eventHandlers;

public interface InputFormListener extends FormListener {
//    void onKeyStroke(Object obj, InputFormArgument arg);
//    void onDelete(Object obj, InputFormArgument arg);
//    void isEmpty(Object obj, InputFormArgument arg);
    void onSubmit(Object obj, InputFormArgument arg);
    void onNotValidCharacter(Object obj, InputFormArgument arg);
}
