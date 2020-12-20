package umidity.cli.forms.prompt;


import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;

public class Prompt {

    @NotNull
    String line;

    @NotNull
    Ansi.Color color;

    @NotNull
    UserPromptTypes type;

    public Prompt(String line){
        this.line = line;
        type = UserPromptTypes.String;
        color = Ansi.Color.BLACK;
    }

    public void setLine(String line){ this.line = line; }
    public String getLine(){ return line; }

    public void setColor(Ansi.Color color){ this.color = color; }
    public Ansi.Color getColor(){return color; }

    public void setType(UserPromptTypes type){ this.type = type; }
    public UserPromptTypes getType(){ return type; }

    @Override
    public String toString(){
        return color.toString() + type + "::" + line;
    }
}
