package com.umidity.gui;
import com.umidity.Main;

import javax.swing.*;

/**
 * This clas is used to handle Settings Frame
 */
public class SettingsFrame extends JFrame {

    /**
     * SettingsFrame Constructor
     */
    public SettingsFrame() {
        SettingsGui settingsGui=new SettingsGui();
        this.setContentPane(settingsGui.panelSettings);
        Main.dbms.addListener(settingsGui);
        this.setVisible(true);
        this.setTitle("Settings");
        this.setSize(450, 300 );
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setResizable(false);
        ImageIcon icon = new ImageIcon("assets/icon64.png");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
    }
}
