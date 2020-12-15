package umidity;

import java.util.Vector;

/**
 * This class provides the user settings
 */
public class UserSettings {
    static public class interfaceSettings{
        /**
         * Theme used by the user
         */
        static public String guiUserTheme; //TODO: temi per la gui
        /**
         * Theme used by the user
         */
        static public String cliUserTheme; //TODO: temi per la cli
        /**
         * If true, the GUI will be launched instead of CLI.
         */
        static public boolean guiEnabled;
    }

    static public class apiSettings{
        /**
         * Show results with the unit mode selected
         */
        static public umidity.api.EUnits units;
    }

    /**
     * Username, only used to identify different settings on the same pc.
     * Its an identifier.
     */
    static public String username;
    /**
     * Array of the city ids the user marked as favorites.
     * These will also automatically generate statistics.
     * Max 10 cities at a time.
     */
    static public String[] cityIds = new String[10];


    //TODO: altre impostazioni utili


}
