package umidity.gui;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(){
        this.setContentPane(new MainGui().panelMain);
        this.setVisible(true);
        this.setTitle("Umidity");
        this.setSize(750, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
//        this.getContentPane().setBackground(new Color(130,130,130));
        ImageIcon icon= new ImageIcon("icon.png");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        //this.pack();

    }
}
