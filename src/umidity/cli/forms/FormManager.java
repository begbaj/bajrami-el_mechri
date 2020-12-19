package umidity.cli.forms;

public class FormManager {




    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
