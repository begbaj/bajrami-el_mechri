package umidity.cli.frames.eventHandlers;

public class InputFormArgument {
    String message;

    public InputFormArgument(){
        message = "";
    }
    public String getMessage(){ return message; }

    public InputFormArgument(String message){
        this.message = message;
    }
}
