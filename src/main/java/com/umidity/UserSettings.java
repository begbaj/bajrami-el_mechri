package com.umidity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.umidity.api.caller.EUnits;

import java.util.Vector;

/**
 * This class provides the user settings
 */
public class UserSettings {

    //region Properties
    /**
     * If true, the GUI will be launched instead of CLI by default.
     */
    private boolean guiEnabled;
    /**
     * Theme used by the user
     */
    private String guiTheme;
    /**
     * Custom prompt for cli
     */
    private String cliPrompt;
    /**
     * Show results with the unit mode selected
     */
    private EUnits units;
    private String apikey;
    /**
     * Array of the city ids the user marked as favorites.
     * These will also automatically generate statistics.
     * Max 10 cities at a time.
     */
    private Vector<String> cityIds = new Vector<>();
    //endregion


    public UserSettings(){
        guiEnabled=true;
        guiTheme ="Light";
        apikey="";
    }

    public void setGuiTheme(String theme){
        guiTheme = theme;
    }
    public void setGuiEnabled(boolean guiEnabled) {
        this.guiEnabled = guiEnabled;
    }
    public void setCliPrompt(String cliPrompt) {
        this.cliPrompt = cliPrompt;
    }
    public void setApikey(String apikey) {
        this.apikey = apikey;
    }
    public void setCityIds(Vector<String> cityIds) {
        this.cityIds = cityIds;
    }
    public void setUnits(EUnits units) {
        this.units = units;
    }

    @JsonProperty("gui")
    public boolean isGuiEnabled() {
        return guiEnabled;
    }
    @JsonProperty("gui_theme")
    public String getGuiTheme() {
        return guiTheme;
    }
    @JsonProperty("cli_prompt")
    @JsonIgnore
    public String getCliPrompt() {
        return cliPrompt;
    }
    @JsonProperty("api_units")
    @JsonIgnore
    public EUnits getUnits() {
        return units;
    }
    @JsonProperty("api_key")
    public String getApikey() {
        return apikey;
    }
    @JsonIgnore
    public Vector<String> getCityIds() {
        return cityIds;
    }
}
