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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuPanel extends JPanel {
        private Game2 window;
        private JLayeredPane pane;
        Action action;

        CustomButton controls;
        CustomButton joinGame;
        CustomButton createGame;
        Image background;
        BufferedImage createGameIcon;
        BufferedImage joinGameIcon;
        BufferedImage controlsIcon;
        
        JPanel panel;


        MenuPanel(Game2 window) {
        	
        	this.panel = this;
            this.window = window;
            this.addMouseListener(new MyMouseListener());
            this.setLayout(new BorderLayout());
            pane = new JLayeredPane();
            pane.setBounds(0, 0, 800, 600);

            try {
                background = ImageIO.read(new File("res/menubackground.png"));
            } catch (IOException e) {
                System.err.println("Error loading image");
            }
        
            joinGame = new CustomButton("Join Game", 350, 300, 200, 50, Color.black);
            createGame = new CustomButton("Create Game", 400, 400, 250, 50, Color.black);
            controls = new CustomButton("", 750, 0, 50, 50, null);


            add(pane);
            pane.setVisible(true);

            this.setVisible(true);

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 0,0, 800, 600, null);

            joinGame.draw(g, this);
            createGame.draw(g, this);
            controls.draw(g, this);



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
            	            	
                if (controls.isMouseOnButton(panel)) { // back button returns to teacher dashboard
                	window.changeState(1);
                } else if (joinGame.isMouseOnButton(panel)) {
                	window.changeState(2);
                } else if (createGame.isMouseOnButton(panel)) {
                	window.changeState(4); // go to character
                } else {
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
