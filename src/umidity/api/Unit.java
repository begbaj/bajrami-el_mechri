package umidity.api;

public enum Unit {
    Standard(""), Metric("metric"), Imperial("imperial");

    private final String action;
    public String getAction()
    {
        return this.action;
    }
    private Unit(String action)
    {
        this.action = action;
    }
}
