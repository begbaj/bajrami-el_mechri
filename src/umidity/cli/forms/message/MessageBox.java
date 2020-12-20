package umidity.cli.forms.message;

import umidity.cli.forms.Form;

public class MessageBox implements Form {

    protected String name;
    protected Object content;
    protected boolean isVisible;

    public void show(){
        if(isVisible)
            if(content != null)
                System.out.println(content.toString());
    }

    public void setVisibility(boolean value){ isVisible = value;}
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
    public String getContent() { return content.toString(); }
    public <T> T setContent(T value){ content = value.toString(); return value;}

}
