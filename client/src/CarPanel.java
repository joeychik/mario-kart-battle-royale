/**
* [CarPanel.java]
* 
* Panel where the user chooses the car that they use.
* 
* @author  Yash Arora
* @since   2019-01-22
*/

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CarPanel extends JPanel {

	// Required car panel variables
    private Game window;
    private JLayeredPane pane;
    private Action action;
    private JButton brickBlock;
    private JButton coinBlock;
    private JButton pipe;
    private Image background;
    private String characterSprite;
    private String carSprite;  
    private CustomSelectionPane selectCar;
    private int carValue;
    private int xPoz;
    private String[] characters = new String[] {"Car1", "Car2", "Car3"};
    private Image[] images = new Image[3];
 
    /**
     * Constructor that makes the car panel window
     * @param window that is the game object
     */
    CarPanel(Game window) {
    	
    	// Required variables
        this.window = window;
        this.setLayout(new BorderLayout());
        pane = new JLayeredPane();
        pane.setBounds(0, 0, 800, 600);
        
        // Load the images
        try {
            images[0] = ImageIO.read(new File("res/car1.png"));
            images[1] = ImageIO.read(new File("res/car2.png"));
            images[2] = ImageIO.read(new File("res/car3.png"));
        } catch (IOException e) {
            System.err.println("Error loading image");
        }
        
        // Make a custom selection pane
        selectCar = new CustomSelectionPane(characters, images);

        // Add to the window
        add(selectCar);

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
        
        // Background
        g.drawImage(background, 0,0, 800, 600, null);
        
        // Change to the ready screen if done
        if (selectCar.getSelectedItemValue() != -1) {
        	setCarValue(selectCar.getSelectedItemValue());
        	window.changeState(6);
        }
        
        // Call again
        repaint();
    }

    /**
     * method that returns value of the car selected
     * @return int carValue that is said value
     */
    public int getCarValue() {
		return carValue;
	}

    /**
     * method that sets the car value
     * @param carValue to set
     */
	public void setCarValue(int carValue) {
		this.carValue = carValue;
		window.setCarVal(carValue);
	}
}
