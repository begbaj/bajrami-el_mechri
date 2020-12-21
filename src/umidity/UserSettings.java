package umidity;

import java.util.Vector;

/**
 * This class provides the user settings
 */
public class UserSettings { //TODO: IDEA:METTERE CHE POSSO ASSUMERE SOLO VALORE PRE CONFIGURATI PER EVITARE ERRORI

    public class InterfaceSettings{
        /**
         * Theme used by the user
         */
        public String guiUserTheme; //TODO: temi per la gui
        /**
         * Theme used by the user
         */
        public String cliUserTheme; //TODO: temi per la cli
        /**
         * If true, the GUI will be launched instead of CLI by default.
         */
        public boolean guiEnabled;
        public String prompt;
    }
    public class ApiSettings{
        /**
         * Show results with the unit mode selected
         */
        public umidity.api.EUnits units;
    }

    /**
     * Interface related settings
     */
    public InterfaceSettings interfaceSettings = new InterfaceSettings();
    /**
     * Api related settings
     */
    public ApiSettings apiSettings = new ApiSettings();
    /**
     * Username, only used to identify different settings on the same pc.
     * Its an identifier.
     */
    public String username;
    /**
     * Array of the city ids the user marked as favorites.
     * These will also automatically generate statistics.
     * Max 10 cities at a time.
     */
    public String[] cityIds = new String[10];

    //TODO: altre impostazioni utili


}
