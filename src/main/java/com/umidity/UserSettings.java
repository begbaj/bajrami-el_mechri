package com.umidity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.umidity.api.caller.EUnits;

/**
 * This class provides the user settings
 */
public class UserSettings {

    public class InterfaceSettings{
        /**
         * Theme used by the user
         */
        @JsonProperty
        public String guiUserTheme; //TODO: temi per la gui
        /**
         * Theme used by the user
         */
        @JsonProperty
        public String cliUserTheme; //TODO: temi per la cli
        /**
         * If true, the GUI will be launched instead of CLI by default.
         */
        @JsonProperty
        public boolean guiEnabled;

        @JsonIgnore
        public String prompt;

        public InterfaceSettings(){
            guiUserTheme="Light";
            cliUserTheme="Light";
            guiEnabled=true;
        }
    }
    public class ApiSettings {
        /**
         * Show results with the unit mode selected
         */
        @JsonIgnore
        public EUnits units;

        @JsonProperty
        public String apikey;

        public ApiSettings(){
            apikey=""; //NON DOVREBBE DARE ERRORE FINCHE NON CARICO
        }
    }

    /**
     * Interface related settings
     */
    @JsonProperty
    public InterfaceSettings interfaceSettings = new InterfaceSettings();
    /**
     * Api related settings
     */
    @JsonProperty
    public ApiSettings apiSettings = new ApiSettings();
    /**
     * Username, only used to identify different settings on the same pc.
     * Its an identifier.
     */
    @JsonIgnore
    public String username;
    /**
     * Array of the city ids the user marked as favorites.
     * These will also automatically generate statistics.
     * Max 10 cities at a time.
     */
    @JsonIgnore
    public String[] cityIds = new String[10];

    //TODO: altre impostazioni utili

    public UserSettings(){
        interfaceSettings=new InterfaceSettings();
        apiSettings=new ApiSettings();
    }

}
