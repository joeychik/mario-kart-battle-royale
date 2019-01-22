/**
* [CreateGamePanel.java]
* 
* Panel that acts as the lobby before joining game
* 
* @author  Yash Arora
* @since   2019-01-22
*/

// Imports
import javax.imageio.ImageIO; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class CreateGamePanel extends JPanel {
	
		// Important variables
        private Game window;
        private JLayeredPane pane;
        private Action action;
        private Image background;
        private JPanel panel;
        private String ip;
        private CustomButton ready;
        private ArrayList<int[]> players = new ArrayList<int[]>();
        private int characterNumber;
        private int carNumber;

        /**
         * Contructor that creates lobby screen
         * @param window
         */
        CreateGamePanel(Game window) {

        	// Important variables
        	this.panel = this;
            this.window = window;
            this.addMouseListener(new MyMouseListener());
            this.setLayout(new BorderLayout());
            pane = new JLayeredPane();
            pane.setBounds(0, 0, 800, 600);

            // Load images
            try {
                background = ImageIO.read(new File("res/lobbyScreen.png"));
            } catch (IOException e) {
                System.err.println("Error loading image");
            }
            
            add(pane);
            pane.setVisible(true);

            // ready button
            ready = new CustomButton("Ready", 650, 520, 150, 60);

            // Make visible
            this.setVisible(true);

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Car and character ids
            characterNumber = window.getCharacterPanel().getCharacterValue();
            carNumber = window.getCarPanel().getCarValue();

            // Background
            g.drawImage(background, 0, 0, 800, 600, null);

            // Draw custom character
            g.drawImage(Utilities.getCarImages()[carNumber], 100, 150, 200, 300, null);
            g.drawImage(Utilities.getCharacterSpriteImages()[characterNumber], 50 + 100, 100 + 150, 100, 100, null);

            // Draw ready button
            ready.draw(g, panel);
            
            // Call again
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

                    window.ready();

                    // Change to the actual game screen
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
