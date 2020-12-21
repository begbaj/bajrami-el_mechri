package umidity.cli.frames.forms;

public class ScreenText extends Form {
    protected String text;

    public void setText(String text){this.text = text;}

    public void show(){
        super.show();
        System.out.println(text);
    }
}
