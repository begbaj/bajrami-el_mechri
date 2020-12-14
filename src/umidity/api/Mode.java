package umidity.api;

public enum Mode {
    JSON(""), XML("xml"), HTML("html");
    
    private final String action;
    public String getAction()
    {
        return this.action;
    }
    private Mode(String action)
    {
        this.action = action;
    }
}
