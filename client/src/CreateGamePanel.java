import javax.imageio.ImageIO; 
import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateGamePanel extends JPanel {
        private Game2 window;
        private JLayeredPane pane;
        Action action;

        Image background;
        JPanel panel;
        String ip;
        CustomButton ready;
        
        ArrayList<int[]> players = new ArrayList<int[]>();

        int characterNumber;
        int carNumber;

        CreateGamePanel(Game2 window) {
        	
        	
        	this.panel = this;
            this.window = window;
            this.addMouseListener(new MyMouseListener());
            this.setLayout(new BorderLayout());
            pane = new JLayeredPane();
            pane.setBounds(0, 0, 800, 600);

            try {
                background = ImageIO.read(new File("res/lobbyScreen.png"));
            } catch (IOException e) {
                System.err.println("Error loading image");
            }
            


            add(pane);
            pane.setVisible(true);
            
            
            ready = new CustomButton("Ready", 650, 520, 150, 60);            
            
            this.setVisible(true);

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            characterNumber = window.characterPanel.getCharacterValue();
            carNumber = window.carPanel.getCarValue();
//            g.setColor(Color.WHITE);
//            g.fillRect(0, 0, 800, 600);
            g.drawImage(background, 0, 0, 800, 600, null);
            
            
            g.drawImage(Utilities.getCarImages()[carNumber], 100, 150, 200, 300, null);
            g.drawImage(Utilities.getCharacterSpriteImages()[characterNumber], 50 + 100, 100 + 150, 100, 100, null);

            
           // g.fillRect(0, 0, 800, 600);
            
            
            ready.draw(g, panel);
            repaint();
        }
        
        public class MyMouseListener implements MouseListener {
            public void mouseEntered(MouseEvent e) {

            }

            /**
             * Checks which button was clicked and acts accordingly
             *
             * @param e mouse event which occurred
             */
            public void mouseClicked(MouseEvent e) {
            	if (ready.isMouseOnButton(panel)) {
            		window.changeState(7);
            	}
            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {

            }
        }


}
