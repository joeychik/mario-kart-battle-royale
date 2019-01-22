/**
 * [JoinGamePanel.java]  
 * Allows user to join game based on ip
 * @since 2019-01-22
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JoinGamePanel extends JPanel {
        private Game window;
        private JLayeredPane pane;
        private Action action;

        private Image background;
        private JPanel panel;
        private String ip;
        private JTextField field;
        private CustomButton message;
        private CustomButton back;

    /**
     * Constructor
     * @param window
     */
    JoinGamePanel(Game window) {
        	
        	this.panel = this;
            this.window = window;
            this.addMouseListener(new MyMouseListener());
            this.setLayout(new BorderLayout());
            pane = new JLayeredPane();
            pane.setBounds(0, 0, 800, 600);

            //setting textfield properties
            field = new JTextField(8);
            field.setBounds(new Rectangle(300, 200, 200, 60));
            field.setOpaque(false);
            field.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            field.setBackground(null);
            field.setFont(new Font("SansSerif", Font.BOLD, 20));
        	field.setHorizontalAlignment(JTextField.CENTER);
            field.setForeground(Color.white);

            //adding to panel
            pane.add(field);
            add(pane);
            pane.setVisible(true);
            
            //buttons
            message = new CustomButton("Join", 300, 300, 200, 60);
            back = new CustomButton("Menu", 650, 0, 150, 60);
            
            
            this.setVisible(true);

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            //draws textboxes and buttons
            g.fillRect(0, 0, 800, 600);
            message.draw(g, panel);
            back.draw(g, panel);
            repaint();
        }
        
        public class MyMouseListener implements MouseListener {
            public void mouseEntered(MouseEvent e) {

            }

            /**
             * mouseClicked
             * Checks which button was clicked and acts accordingly
             * @param e mouse event which occurred
             */
            public void mouseClicked(MouseEvent e) {
            	if (back.isMouseOnButton(panel)) {
            		window.changeState(0);
            	} else if (message.isMouseOnButton(panel)) {

            	    //connects to server given IP
            		if (field.getText().equals("")) {
            		    //if textfield is blank, default IP is local
                	    window.connectToGame("127.0.0.1", 5000);
            		} else {
            		    //connects to given IP
                	    window.connectToGame(field.getText(), 5000);
            		}
            		window.changeState(6);


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
