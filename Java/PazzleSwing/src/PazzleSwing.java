import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PazzleSwing extends JFrame {
    private PazzlePanel pazzlePanel;
    private static final int MAINSIZEH = 600, MAINSIZEW = 1207;


    PazzleSwing(){
        try {
            pazzlePanel = new PazzlePanel();
            this.setLayout(new BorderLayout());
            this.add(pazzlePanel, BorderLayout.CENTER);
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    static public void main(String[] args){
        PazzleSwing frame = new PazzleSwing();
        frame.setBounds(0, 0, MAINSIZEW, MAINSIZEH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}