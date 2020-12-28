package com.umidity.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.formdev.flatlaf.*;
import com.umidity.Main;
import com.umidity.Coordinates;
import com.umidity.database.CityRecord;

public class SettingsGui implements FocusListener {

    private JComboBox guiComboBox;
    public JPanel panelSettings;
    private JTabbedPane tabbedPane1;
    private JTable cityTable;
    private JButton deleteButton;
    private JPanel Graphics;
    private JPanel User;
    private JComboBox cliComboBox;
    private JComboBox interfaceComboBox;
    private JLabel noCityLabel;

    public SettingsGui() {
        panelSettings.addFocusListener(this);
        Main.dbms.loadUserSettings();
        if(Main.userSettings.interfaceSettings.guiUserTheme.equals("Light"))
            guiComboBox.setSelectedIndex(0);
        else
            guiComboBox.setSelectedIndex(1);
        if(Main.userSettings.interfaceSettings.cliUserTheme.equals("Light"))
            cliComboBox.setSelectedIndex(0);
        else
            cliComboBox.setSelectedIndex(1);
        if(Main.userSettings.interfaceSettings.guiEnabled)
            interfaceComboBox.setSelectedIndex(0);
        else
            interfaceComboBox.setSelectedIndex(1);

        guiComboBox.addActionListener(e -> {
                if(guiComboBox.getSelectedIndex()==0){
                    try {
                        UIManager.setLookAndFeel( new FlatLightLaf() );
                        Main.userSettings.interfaceSettings.guiUserTheme="Light";
                    } catch( Exception ex ) {
                        System.err.println( "Failed to initialize LaF" );
                    }
                }else {
                    try {
                        UIManager.setLookAndFeel( new FlatDarkLaf() );
                        Main.userSettings.interfaceSettings.guiUserTheme="Dark";
                    } catch( Exception ex ) {
                        System.err.println( "Failed to initialize LaF" );
                    }
                }
                Main.dbms.setUserSettings();

                Arrays.stream(JFrame.getFrames()).forEach(x->SwingUtilities.updateComponentTreeUI(x.getComponent(0)));
            });


        tabbedPane1.addChangeListener(e -> {
            if(tabbedPane1.getSelectedIndex()==1){
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
        cliComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cliComboBox.getSelectedIndex()==0){
                        Main.userSettings.interfaceSettings.guiUserTheme="Light";

                }else {
                        Main.userSettings.interfaceSettings.guiUserTheme="Dark";
                }
                Main.dbms.setUserSettings();
            }
        });
        interfaceComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(interfaceComboBox.getSelectedIndex()==0){
                    Main.userSettings.interfaceSettings.guiEnabled=true;

                }else {
                    Main.userSettings.interfaceSettings.guiEnabled=false;
                }
                Main.dbms.setUserSettings();
            }
        });
    }

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

    @Override
    public void focusGained(FocusEvent e) {
            createCityTable();
            System.out.println("Focus gained");
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}

