package umidity.cli.frames.forms;

public abstract class Form {
    boolean enabled = true;
    boolean isVisible = false;

    String name = "";


    public void show(){}

    public void enable(){ enabled = true; }
    public void disable(){ enabled = false; }
    public boolean getEnabled(){ return enabled; }

    /**
     * if true, when show is called, the Form will be showed to the user
     * @param value
     */
    public Form setVisibility(boolean value){isVisible = value; return this;};
    public Form setName(String name){this.name = name; return this;};
    public boolean getVisibility(){return isVisible;}
    public String getName(){return name;};
}
