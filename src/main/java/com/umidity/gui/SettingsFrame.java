package com.umidity.gui;
import javax.swing.*;
import java.awt.*;

public class SettingsFrame extends JFrame {

    public SettingsFrame() {
        this.setContentPane(new SettingsGui().panelSettings);
        this.setVisible(true);
        this.setTitle("Settings");
        this.setSize(300, 300 );
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0, 255, 255));
        ImageIcon icon = new ImageIcon("icon.png");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        //this.pack();
    }
}
