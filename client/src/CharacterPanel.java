import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CharacterPanel extends JPanel {

    /*
    --------FOR YASH--------
    This is the player and car selection class, can you make it look pretty?
    Each character should correspond to a sprite file url
    i made some player cards in the res folder if you wanna see and work off of those

     */

    private Game2 window;
    private JLayeredPane pane;
    Action action;

    JButton brickBlock;
    JButton coinBlock;
    JButton pipe;
    Image background;

    String characterSprite;
    String carSprite;


    CharacterPanel(Game2 window) {
        this.window = window;

        this.setLayout(new BorderLayout());
        pane = new JLayeredPane();
        pane.setBounds(0, 0, 800, 600);


        add(pane);

        this.setVisible(true);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0,0, 800, 600, null);

        repaint();
    }

    private class Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            if (e.getSource() == controlScreen) {
//                window.setControlPanel(new ControlPanel(window));
//                window.changeState(1);
//            } else if (e.getSource() == joinGame) {
//                window.changeState(2);
//            } else if (e.getSource() == createGame) {
//                window.changeState(3);
//            }
        }
    }

}
