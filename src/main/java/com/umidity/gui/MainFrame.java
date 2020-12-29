package com.umidity.gui;
import com.umidity.Main;

import javax.swing.*;

public class MainFrame extends JFrame {
//TODO:DOCUMENTA
    public MainFrame(){
        MainGui maingui=new MainGui();
        this.setContentPane(maingui.panelMain);
        Main.caller.addListener(maingui);
        Main.dbms.addListener(maingui);
        this.setVisible(true);
        this.setTitle("Umidity");
        this.setSize(700, 550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //TODO: system tray
        this.setResizable(false);
        ImageIcon icon= new ImageIcon("assets/icon.png");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
    }
}
