package umidity.cli.frames;
import umidity.cli.frames.forms.Form;

import java.util.*;

public class Frame {
    Vector<Form> forms;
    private String name;

    public Frame(){
        this.forms = new Vector<>();
    }

    public void show(){
        for(Form f:forms){
            f.show();
        }
    }

    public Frame addForm(Form newForm){ forms.add(newForm); return this; }
    public Frame addForms(Collection<Form> forms){ forms.addAll(forms); return this; }

    public Vector<Form> getForms(){ return forms; }
    public <T> T getForm(String formName){
        for(Form f:forms){
            if(f.getName() == formName) return (T)f;
        }
        return null;
    }


    public String getName() { return name; }
    public Frame setName(String name) { this.name = name; return this; }
}
