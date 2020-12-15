package umidity.gui;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(){
        this.setVisible(true);
        this.setTitle("Umidity");
        this.setSize(720, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(50,200,255));
        ImageIcon icon= new ImageIcon("icon.png");
        this.setIconImage(icon.getImage());
        Button b1=new Button();
        this.add(b1);
        b1.setVisible(true);

    }
}
