package umidity.cli.forms.message;

import umidity.cli.forms.Form;

public class MessageBox implements Form {
    String name;
    String message;
    boolean isVisible;

    public void show(){
        if(isVisible)
            System.out.println(message);
    }

    public void setVisibility(boolean value){ isVisible = value;}

    public void setMessage(String newMessage){message = newMessage;}
}
