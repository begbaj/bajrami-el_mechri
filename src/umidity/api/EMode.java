package umidity.api;

public enum EMode {
    JSON(""), XML("xml"), HTML("html");
    
    private final String action;
    public String getAction()
    {
        return this.action;
    }
    EMode(String action)
    {
        this.action = action;
    }
}
