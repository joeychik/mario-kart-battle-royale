import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CharacterPanel extends JPanel {

    private Game window;
    private JLayeredPane pane;
    Action action;

    JButton brickBlock;
    JButton coinBlock;
    JButton pipe;
    Image background;

    String characterSprite;
    String carSprite;
    
    CustomSelectionPane selectCharacter;
    
    private int characterValue;
    
    private boolean hasQuit = false;
    
    int xPoz;
    String[] characters = new String[] {"Brick", "Question Mark", "Pipe"};
    Image[] images = new Image[3];
 
    CharacterPanel(Game window) {
        this.window = window;

        this.setLayout(new BorderLayout());
        pane = new JLayeredPane();
        pane.setBounds(0, 0, 800, 600);
        
        
        try {
            images[0] = ImageIO.read(new File("res/pipecard.png"));
            images[1] = ImageIO.read(new File("res/brickCard.png"));
            images[2] = ImageIO.read(new File("res/coincard.png"));
        } catch (IOException e) {
            System.err.println("Error loading image");
        }
        
        selectCharacter = new CustomSelectionPane(characters, images);

        

        add(selectCharacter);
        this.setVisible(true);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        

        g.drawImage(background, 0,0, 800, 600, null);
        
        
        if (selectCharacter.getSelectedItemValue() != -1 && hasQuit == false) {
        	hasQuit = true;
        	setCharacterValue(selectCharacter.getSelectedItemValue());
        	System.out.println(characterValue);
        	window.changeState(5);
        }

        repaint();
    }

    public int getCharacterValue() {
		return characterValue;
	}

	public void setCharacterValue(int characterValue) {
		this.characterValue = characterValue;
	}

	private class Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

	

}
