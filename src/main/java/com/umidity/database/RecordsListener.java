package com.umidity.database;

/**
 * Database events listener interface.<br>
 *
 * Implement this interface whenever you need to handle DatabaseManager events. Used in the GUI classes to update state.<br>
 * To add your listener to the "list of listeners" of a DatabaseManager instance, just call the <em>addListener(yourListener)</em> method.
 */
public interface RecordsListener {
    /**
     * Launches when the saved city list is changed
     */
    void onChangedCities();
}
