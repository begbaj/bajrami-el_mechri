package umidity.cli.forms.message;

import umidity.cli.forms.message.MessageBox;

import java.util.Vector;

public class MenuBox extends MessageBox {

    Vector<Object> list = new Vector<>();

    public void clear(){list = new Vector<>(); }
    public void add(Object obj){ list.add(obj); }
    public void rem(Object obj){ list.remove(obj); }

    /**
     * -1: will not print zero
     * 0: will start from zero
     * 1: will end with zero
     * @param startFromZero
     */
    public void refresh(int startFromZero){
        content = "";

        int count = 0;

        if(startFromZero>0){
            count = 1;
            int size = list.toArray().length;
            for(Object o:list){
                if(count < size) content += count++ + ")" + o.toString() + "\n";
                else content +=  "0)" + o.toString() + "\n";
            }
            return;
        }
        else if(startFromZero<0){
            count = 1;
        }
        for(Object o:list){
            content += count++ + ")" + o.toString() + "\n";
        }
    }
}
