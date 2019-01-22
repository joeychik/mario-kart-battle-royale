/**
* [CharacterPanel.java]
* 
* Panel where the user chooses the character that they use.
* 
* @author  Yash Arora
* @since   2019-01-22
*/

// Imports
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CharacterPanel extends JPanel {

	// Important variables
    private Game window;
    private JLayeredPane pane;
    private Action action;
    private JButton brickBlock;
    private JButton coinBlock;
    private JButton pipe;
    private Image background;
    private String characterSprite;
    private String carSprite;   
    private CustomSelectionPane selectCharacter;
    private int characterValue;
    private boolean hasQuit = false;
    private int xPoz;
    private String[] characters = new String[] {"Brick", "Question Mark", "Pipe"};
    private Image[] images = new Image[3];
 
    /**
     * Constructor that makes char selection screen
     * @param window
     */
    CharacterPanel(Game window) {
    	
    	// Required settings
        this.window = window;
        this.setLayout(new BorderLayout());
        pane = new JLayeredPane();
        pane.setBounds(0, 0, 800, 600);
        
        // Load images
        try {
            images[0] = ImageIO.read(new File("res/pipecard.png"));
            images[1] = ImageIO.read(new File("res/brickCard.png"));
            images[2] = ImageIO.read(new File("res/coincard.png"));
        } catch (IOException e) {
            System.err.println("Error loading image");
        }
        
        // Set selected character
        selectCharacter = new CustomSelectionPane(characters, images);

        // Add to screen
        add(selectCharacter);
        
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
        
        // Change to car selection once done
        if (selectCharacter.getSelectedItemValue() != -1 && hasQuit == false) {
        	hasQuit = true;
        	setCharacterValue(selectCharacter.getSelectedItemValue());
        	window.changeState(5);
        }

        // Call again
        repaint();
    }

    /**
     * method that returns character value
     * @return int characterValue
     */
    public int getCharacterValue() {
		return characterValue;
	}

    /**
     * method that sets the character value
     * @param characterValue int to be set
     */
	public void setCharacterValue(int characterValue) {
		this.characterValue = characterValue;
		window.setCharVal(characterValue);
	}	

}
