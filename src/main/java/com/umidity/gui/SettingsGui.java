package com.umidity.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import com.umidity.Main;
import com.umidity.Coordinates;
import com.umidity.database.CityRecord;
import com.umidity.database.RecordsListener;

/**
 * This class handles SettngsFrame GUI
 */
public class SettingsGui implements RecordsListener {

    //region Components
    private JComboBox guiComboBox;
    public JPanel panelSettings;
    private JTabbedPane tabbedSettings;
    private JTable cityTable;
    private JButton deleteButton;
    private JPanel Graphics;
    private JPanel User;
    private JComboBox interfaceComboBox;
    private JLabel noCityLabel;
    private JTextField textField_API;
    private JButton apiKeyButton;
    private JLabel interfaceLabel;
    //endregion

    /**
     * SettingsGui constructor
     */
    public SettingsGui(){
        init();

        guiComboBox.addActionListener(e -> {
                    try {
                        if (guiComboBox.getSelectedIndex() == 0) {
                            MainGui.changeTheme("Light");
                            Main.userSettings.setGuiTheme("Light");
                        } else {
                            MainGui.changeTheme("Dark");
                            Main.userSettings.setGuiTheme("Dark");
                        }
                        Main.dbms.saveUserSettings();

                    } catch( Exception ex ) {
                        System.err.println( "Failed to initialize LaF" );
        }});

        tabbedSettings.addChangeListener(e -> {
            if(tabbedSettings.getSelectedIndex()==0){
                createCityTable();
                interfaceLabel.setText("");
                textField_API.setText(Main.userSettings.getApikey());
            }else if(tabbedSettings.getSelectedIndex()==1){
                createCityTable();
                noCityLabel.setText("");
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                Main.dbms.removeCity(new CityRecord(Integer.parseInt((String)cityTable.getValueAt(cityTable.getSelectedRow(), 1)), "", new Coordinates(-1,-1)));
                createCityTable();
                noCityLabel.setText("");
            }catch (Exception exception){
                noCityLabel.setText("No city selected!");
            }

        });

        interfaceComboBox.addActionListener(e -> {
            if(interfaceComboBox.getSelectedIndex()==0){
                Main.userSettings.setGuiEnabled(true);

            }else {
                Main.userSettings.setGuiEnabled(false);
            }
            Main.dbms.saveUserSettings();
            interfaceLabel.setText("Changes will be applied on next startup.");
        });

        apiKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.userSettings.setApikey(textField_API.getText());
                Main.caller.setAppid(textField_API.getText());
                Main.asyncCaller.setAppid(textField_API.getText());
            }
        });
    }

    /**
     * Perform initialization actions
     */
    private void init(){
        switch (Main.userSettings.getGuiTheme()){
            case "Dark" -> guiComboBox.setSelectedIndex(1);
            default -> guiComboBox.setSelectedIndex(0);
        }
        if (Main.userSettings.isGuiEnabled()) {
            interfaceComboBox.setSelectedIndex(0);
        } else {
            interfaceComboBox.setSelectedIndex(1);
        }
        textField_API.setText(Main.userSettings.getApikey());
    }

    /**
     * Creates the saved cities table in the user tab
     */
    public void createCityTable(){
        List<CityRecord> cities= Main.dbms.getCities();
        Vector<Vector<String>>data=new Vector<>();
        for(CityRecord record:cities){
            Vector<String> data_info=new Vector<>(2);
            data_info.add(record.getName());
            data_info.add(String.valueOf(record.getId()));
            data.add(data_info);
        }
        Vector<String> header=new Vector<>(2);
        header.add("Name");
        header.add("ID");
        cityTable.setModel(new DefaultTableModel(data,header));
        cityTable.setFillsViewportHeight(true);
        cityTable.setDefaultEditor(Object.class, null);
    }

    /**
     * When launched updates cityTable
     */
    @Override
    public void onChangedCities() {
            createCityTable();
    }
}

