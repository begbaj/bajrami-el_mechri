package com.umidity.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

import com.formdev.flatlaf.*;
import com.umidity.api.response.Coordinates;
import com.umidity.database.CityRecord;
import com.umidity.database.DatabaseManager;

public class SettingsGui {

    private JComboBox guiComboBox;
    public JPanel panelSettings;
    private JTabbedPane tabbedPane1;
    private JTable cityTable;
    private JButton deleteButton;
    private JPanel Graphics;
    private JPanel User;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private final DatabaseManager DBMS=new DatabaseManager();
    public SettingsGui() {

        guiComboBox.addActionListener(e -> {
                if(guiComboBox.getSelectedIndex()==0){
                    try {
                        UIManager.setLookAndFeel( new FlatLightLaf() );
                    } catch( Exception ex ) {
                        System.err.println( "Failed to initialize LaF" );
                    }
                }else {
                    try {
                        UIManager.setLookAndFeel( new FlatDarkLaf() );
                    } catch( Exception ex ) {
                        System.err.println( "Failed to initialize LaF" );
                    }
                }
            SwingUtilities.updateComponentTreeUI(panelSettings);
            });


        tabbedPane1.addChangeListener(e -> {
            if(tabbedPane1.getSelectedIndex()==1){
                createCityTable();
            }
        });
        deleteButton.addActionListener(e -> {
            DBMS.removeCity(new CityRecord(Integer.parseInt((String)cityTable.getValueAt(cityTable.getSelectedRow(), 1)), "", new Coordinates(-1,-1)));
            createCityTable();
        });
    }

    public void createCityTable(){
        List<CityRecord> cities=DBMS.getCities();
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
    }
}

