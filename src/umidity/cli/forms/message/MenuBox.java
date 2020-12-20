package umidity.cli.forms.message;

import umidity.cli.forms.message.MessageBox;

import java.util.Vector;

public class MenuBox extends MessageBox {

    Vector<Object> list = new Vector<>();

    public void clear(){list = new Vector<>(); }
    public void add(Object obj){ list.add(obj); }
    public void rem(Object obj){ list.remove(obj); }
    public void refresh(boolean startFromZero){
        content = "";
        int count;

        if(startFromZero) count = 0;
        else count =1;

        for(Object o:list){
            content += count++ + ")" + o.toString() + "\n";
        }
    }

}
