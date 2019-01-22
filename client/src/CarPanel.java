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

public class CarPanel extends JPanel {

    private Game window;
    private JLayeredPane pane;
    Action action;

    JButton brickBlock;
    JButton coinBlock;
    JButton pipe;
    Image background;

    String characterSprite;
    String carSprite;
    
    CustomSelectionPane selectCar;
    
    private int carValue;
    
    int xPoz;
    String[] characters = new String[] {"Car1", "Car2", "Car3"};
    Image[] images = new Image[3];
 
    CarPanel(Game window) {
        this.window = window;

        this.setLayout(new BorderLayout());
        pane = new JLayeredPane();
        pane.setBounds(0, 0, 800, 600);
        
        
        try {
            images[0] = ImageIO.read(new File("res/car1.png"));
            images[1] = ImageIO.read(new File("res/car2.png"));
            images[2] = ImageIO.read(new File("res/car3.png"));
        } catch (IOException e) {
            System.err.println("Error loading image");
        }
        
        selectCar = new CustomSelectionPane(characters, images);

        

        add(selectCar);

        this.setVisible(true);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        

        g.drawImage(background, 0,0, 800, 600, null);
        
        if (selectCar.getSelectedItemValue() != -1) {
        	setCarValue(selectCar.getSelectedItemValue());
        	window.changeState(6);
        }

        repaint();
    }

    public int getCarValue() {
		return carValue;
	}

	public void setCarValue(int carValue) {
		this.carValue = carValue;
		window.setCarVal(carValue);
	}

	private class Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

	

}
