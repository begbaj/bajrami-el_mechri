package umidity.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;

import com.formdev.flatlaf.*;
import umidity.database.CityRecord;
import umidity.database.DatabaseManager;
import umidity.database.HumidityRecord;

public class SettingsGui {

    private JComboBox guiComboBox;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    public JPanel panelSettings;
    private JTabbedPane tabbedPane1;
    private JPanel Graphics;
    private JPanel User;
    private JTable cityTable;
    private DatabaseManager DBMS=new DatabaseManager();
    public SettingsGui() {
//        try {
//            UIManager.setLookAndFeel(UIManager.get());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        guiComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                }
        });


        tabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(tabbedPane1.getSelectedIndex()==1){
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
        });
    }
}

