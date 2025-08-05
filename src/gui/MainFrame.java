package gui;


import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {
    
    public static void main(String[] args) {
        try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
        e.printStackTrace();
    }
        Frame2 frame = new Frame2();
        frame.setTitle("Image Library");
        frame.setSize (1500,800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
