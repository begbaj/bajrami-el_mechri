package com.umidity.gui;
import com.umidity.Main;

import javax.swing.*;
import java.awt.*;

public class SettingsFrame extends JFrame {

    public SettingsFrame() {
        SettingsGui settingsGui=new SettingsGui();
        this.setContentPane(settingsGui.panelSettings);
        Main.dbms.addListener(settingsGui);
        this.setVisible(true);
        this.setTitle("Settings");
        this.setSize(300, 300 );
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setResizable(false);
        ImageIcon icon = new ImageIcon("assets/icon64.png");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
    }
}
