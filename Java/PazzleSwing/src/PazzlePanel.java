import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PazzlePanel extends JPanel {
    private static final int MAINSIZEH = 600, MAINSIZEW = 1200, SIZEH = 3, SIZEW = 3;
    private JPanel panelForOriginal;
    private JPanel panelForBlocks;
    private ImageIcon original;
    private BufferedImage bufOrig;
    private ModelOfPermutation permutationModel;
    private Image img;
    JButton button;
    GridBagConstraints c = new GridBagConstraints();

    public PazzlePanel() throws IOException{
        this.setLayout(new BorderLayout());
        chooseFile();

        panelForOriginal = new JPanel();
        panelForBlocks = new JPanel();
        panelForBlocks.setLayout(new GridBagLayout());

        JButton buttonForOrig = new JButton(original);
        buttonForOrig.setEnabled(true);
        panelForOriginal.add(buttonForOrig, BorderLayout.CENTER);

        permutationModel = new ModelOfPermutation(bufOrig, SIZEH, SIZEW);

        MouseAdapter globListener = new MouseAdapter() {
            int coordW1, coordH1, coordW2, coordH2;
            JButton temp1Button, temp2Button;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                temp1Button = (JButton)e.getSource();
                coordW1 = e.getXOnScreen();
                coordW1 /= (MAINSIZEW/(2*SIZEW));
                coordH1 = e.getYOnScreen();
                coordH1 /= (MAINSIZEH/SIZEH);
            }

            @Override
            public void mouseReleased(MouseEvent e){
                super.mouseReleased(e);
                if(e.getXOnScreen() > panelForBlocks.getWidth() || e.getYOnScreen() > panelForBlocks.getHeight()){
                    JOptionPane.showMessageDialog(null, "Exception");
                }
                else{
                    coordW2 = e.getXOnScreen();
                    coordW2 /= (MAINSIZEW / (2 * SIZEW));
                    coordH2 = e.getYOnScreen();
                    coordH2 /= (MAINSIZEH / SIZEH);
                    permutationModel.swap(coordH1, coordW1, coordH2, coordW2);
                    //panelForBlocks.remove(temp1Button);
                    //panelForBlocks.remove(temp2Button);
                    c.gridx = coordW2;
                    c.gridy = coordH2;
                    panelForBlocks.add(temp1Button, c);
                    c.gridx = coordW1;
                    c.gridy = coordH1;
                    panelForBlocks.add(temp2Button, c);
                    panelForBlocks.repaint();
                    panelForBlocks.updateUI();
                    if (permutationModel.isSuccess()) {
                        JOptionPane.showMessageDialog(null, "You win!!!");
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                temp2Button = (JButton)e.getSource();
            }
        };

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        for (int i = 0; i < SIZEH; i++) {
            for(int j = 0; j < SIZEW; j++) {
                button = new JButton(permutationModel.getCell(i,j));
                button.setPreferredSize(button.getPreferredSize());
                button.addMouseListener(globListener);
                button.setEnabled(true);
                panelForBlocks.add(button, c);
                c.gridx++;
            }
            c.gridx = 0;
            c.gridy++;
        }

        panelForOriginal.setPreferredSize(new Dimension(MAINSIZEW/2 - 7, MAINSIZEH));
        panelForBlocks.setPreferredSize(new Dimension(MAINSIZEW/2 - 7, MAINSIZEH));
        this.add(panelForOriginal, BorderLayout.EAST);
        this.add(panelForBlocks, BorderLayout.WEST);
    }

    private void chooseFile() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int ret = fileChooser.showOpenDialog(null);

        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            bufOrig = ImageIO.read(file);
            img = bufOrig.getScaledInstance(MAINSIZEW/2,MAINSIZEH,Image.SCALE_SMOOTH);
            original = new ImageIcon(img);
        } else {
            throw new FileNotFoundException("File not found");
        }
    }
}
