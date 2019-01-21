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

public class JoinGamePanel extends JPanel {
        private Game2 window;
        private JLayeredPane pane;
        Action action;

        Image background;
        JPanel panel;
        String ip;
        JTextField field;
        CustomButton message;
        CustomButton back;


        JoinGamePanel(Game2 window) {
        	
        	this.panel = this;
            this.window = window;
            this.addMouseListener(new MyMouseListener());
            this.setLayout(new BorderLayout());
            pane = new JLayeredPane();
            pane.setBounds(0, 0, 800, 600);

//            try {
//                background = ImageIO.read(new File("res/menubackground.png"));
//            } catch (IOException e) {
//                System.err.println("Error loading image");
//            }

            field = new JTextField(8);
            field.setBounds(new Rectangle(300, 200, 200, 60));
            field.setOpaque(false);
            field.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            field.setBackground(null);
            field.setFont(new Font("SansSerif", Font.BOLD, 20));
        	field.setHorizontalAlignment(JTextField.CENTER);
            field.setForeground(Color.white);

            pane.add(field);
            add(pane);
            pane.setVisible(true);
            
            
            message = new CustomButton("Join", 300, 300, 200, 60);
            back = new CustomButton("Menu", 650, 0, 150, 60);
            
            
            this.setVisible(true);

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //g.drawImage(background, 0,0, 800, 600, null);

            g.fillRect(0, 0, 800, 600);
            message.draw(g, panel);
            back.draw(g, panel);
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
            	if (back.isMouseOnButton(panel)) {
            		window.changeState(0);
            	} else if (message.isMouseOnButton(panel)) {
            		// DO SOMETHING HERE
                    // window.connectToGame(server ip, port this should be 5000);
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
