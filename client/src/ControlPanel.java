/**
* [ControlPanel.java]
* 
* Panel that shows users the controls
* 
* @author  Yash Arora
* @since   2019-01-22
*/

// Imports
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class ControlPanel extends JPanel {
	
	// Important variables
    private Game window;
    private JLayeredPane pane;
    private Action action;
    private BufferedImage menuIcon;
    private JButton menuScreen;
    private Image background;

    /**
     * Contructor that makes the panel
     * @param window
     */
    ControlPanel(Game window) {
    	
    	// Important settings
        this.window = window;
        this.setLayout(new BorderLayout());
        pane = new JLayeredPane();
        pane.setBounds(0, 0, 800, 600);

        // Load images
        try {
            background = ImageIO.read(new File("res/Control Page.png"));
            menuIcon = ImageIO.read(new File("res/menu.png"));
        } catch (IOException e) {
            System.err.println("Error loading image");
        }

        // Button to go to menu
        menuScreen = new JButton(new ImageIcon(menuIcon.getScaledInstance(200, 50, 0)));
        menuScreen.setBounds(window.getWidth() - 250, 50, 200, 50);
        menuScreen.setBorder(BorderFactory.createEmptyBorder());
        menuScreen.setContentAreaFilled(false);
        action = new Action();
        menuScreen.addActionListener(action);
        add(pane);
        
        // Add pane to panel
        pane.add(menuScreen);
        
        // Set visible
        this.setVisible(true);

    }

    /**
     * paintComponent
     * @param g Graphics object used to draw
     * 
     * draws to the screen
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background
        g.drawImage(background, 0,-50, 800, 650, null);

        // Call again
        repaint();
    }

    // Class for actions
    private class Action implements ActionListener {
    	
    	/**
    	 * overrides method for button actions
    	 */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == menuScreen) {
            	
            	// Go back to menu
                window.changeState(0);
            }
        }
    }


}
