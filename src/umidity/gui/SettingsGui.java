package umidity.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.*;

public class SettingsGui {

    private JComboBox guiComboBox;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    public JPanel panelSettings;
    private JTabbedPane tabbedPane1;
    private JPanel Graphics;
    private JPanel User;
    private JTable table1;

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
    }
}
