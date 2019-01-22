/**
* [CustomSelectionPane.java]
* 
* Panel where the user chooses a car or character
* 
* @author  Yash Arora
* @since   2019-01-22
*/

// Imports
import java.awt.BasicStroke; 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.IllegalComponentStateException;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;


public class CustomSelectionPane extends JPanel implements KeyListener{
	
	// Important class variables
    private String[] items;
    private Image[] images;
    private int xPoz = 0;
    private int selectedItemValue = -1;
	
    /**
     * Constructor
     * @param items Array of the items in the menu
     * @param title String name of the menu
     */
	public CustomSelectionPane(String[] items, Image[] images) {
		this.items = items;
		this.images = images;
		this.setPreferredSize(new Dimension(800, 600));
		
		// Request focus
		this.addKeyListener(this);
		this.setFocusable(true);
        this.requestFocusInWindow();
        this.requestFocus();

        // Set visible
        setVisible(true);
	}
	
	/**
	 * getSelectedItemValue
	 * @return selectedItemValue of the object selected
	 */
	public int getSelectedItemValue() {
		return selectedItemValue;
	}

	
    /**
     * paintComponent
     * draws all buttons and text on screen
     * @param g Graphics
     */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
				
		// For better graphics
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		
		// Draw pictures
		for (int i = 0; i < images.length; i++) {
	        g.drawImage(images[i], 180 + 200*i, 100, 200, 300, null);
			g2.drawRect(180 + 200*i, 100, 200, 300);
		}
		
		// Draw selected image
		g2.drawRect(40, 75 + 100, 100, 150);
        g.drawImage(images[xPoz], 40, 75 + 100, 100, 150, null);

        // Draw borders
		g2.setStroke(new BasicStroke(10));
		g2.setColor(Color.gray);
		g2.drawRect(180 + 200*xPoz, 100, 200, 300);
				
		// Request focus for keylistener
		if (this.isVisible()) {
			this.setFocusable(true);
	        this.requestFocusInWindow();
	        this.requestFocus();
		}
			
		// Call again
		repaint();
	}

    /**
     * isMouseOnPanel
     * Checks if the mouse is over the menu
     * @param panel that the menu is on
     * @return true if on and false if not
     * @throws IllegalComponentStateException
     */
	public boolean isMouseOnPanel(JPanel panel) throws IllegalComponentStateException{
		Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        Point relScreenLocation = panel.getLocationOnScreen().getLocation();
        int x = (int) Math.round(mouseLocation.getX() - relScreenLocation.getX());
        int y = (int) Math.round(mouseLocation.getY() - relScreenLocation.getY());

        // Return if mouse is on panel
        return ((x >= 0) && (x <= panel.getWidth()) && (y >= 0) && (y <= panel.getHeight()));
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

	    if (key == KeyEvent.VK_A) {
	        if (xPoz > 0) {
	        	xPoz--; // Go left
	        }
	    }
	    if (key == KeyEvent.VK_D) {
	        if (xPoz < items.length-1) {
	        	xPoz++; // Go right
	        }
	    }
	    if (key == KeyEvent.VK_ENTER) {
	        selectedItemValue = xPoz; // Find the selected value
	        this.setVisible(false);
	    }
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}

