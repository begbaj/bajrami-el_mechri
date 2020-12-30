package com.umidity.gui;
import com.umidity.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class is used to handle program's main Frame<br>
 *
 * MainFrame allows you to add the program into the System Tray instead of closing it.
 */
public class MainFrame extends JFrame {
private TrayIcon trayIcon;
private PopupMenu popupMenu;
private MainGui maingui;

    /**
     * Frame's constructor
     */
    public MainFrame(){
        maingui=new MainGui();
        this.setContentPane(maingui.panelMain);
        Main.caller.addListener(maingui);
        Main.dbms.addListener(maingui);
        this.setVisible(true);
        this.setTitle("Umidity");
        this.setSize(700, 550);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        ImageIcon icon= new ImageIcon("assets/icon64.png");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                int i=JOptionPane.showConfirmDialog(null, "Minimize the program?");
                if(i==0){
                    //Check the SystemTray is supported
                    if (!SystemTray.isSupported()) {
                        System.out.println("SystemTray is not supported");
                        return;
                    }
                    MainFrame.this.setVisible(false);
                    createSystemTray();
                }
                if(i==1) System.exit(0);
            }
        });
    }

    /**
     * Adds program into System Tray
     */
    private void createSystemTray() {
        try {
            if (SystemTray.isSupported()) {
                popupMenu = createPopupMenu();
                SystemTray systemTray = SystemTray.getSystemTray();
                Image img = Toolkit.getDefaultToolkit().getImage("assets/icon16.png");
                trayIcon = new TrayIcon(img);
                trayIcon.setPopupMenu(popupMenu);
                systemTray.add(trayIcon);
                trayIcon.addMouseListener(new SystemTrayMouseListener());
            }
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Creates standard PopupMenu
     * @return the PopupMenu
     */
    private PopupMenu createPopupMenu() {

        PopupMenu p = new PopupMenu();
        MenuItem open = new MenuItem("Open");
        MenuItem exit = new MenuItem("Exit");

        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
                SystemTray.getSystemTray().remove(trayIcon);
            }
        });

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                SystemTray.getSystemTray().remove(trayIcon);
                System.exit(0);
            }
        });

        p.add(open);
        p.add(exit);
        return p;
    }

    /**
     * Listener used to open program when clicked with left mouse button
     */
    private class SystemTrayMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == 1) {
                if (isVisible()) {
                    setVisible(false);
                } else {
                    setVisible(true);
                    SystemTray.getSystemTray().remove(trayIcon);
                }
            }
        }
    }
}
