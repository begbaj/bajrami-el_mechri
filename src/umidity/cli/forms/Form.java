package umidity.cli.forms;

public interface Form {
    boolean isVisible = false;
    String name = "form";
    Object content = new Object();

    void show();

    void setVisibility(boolean value);

    void setName(String name);
    String getName();

    <T> T setContent(T value);
    <T> T getContent();
}
