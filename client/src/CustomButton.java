/**
 * [CustomButton.java]  
 * the custom class button that can be selected
 * BEST OF ALL IT LOOKS SO MUCH BETTER THAN JAVA BUTTONS
 * @author Yash Arora
 * @since 2019-01-22
 */
 
// Imports
import javax.swing.JPanel; 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.IllegalComponentStateException;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.awt.MouseInfo;

/**
 * Custom button class that looks good
 * @author Yash Arora
 */
public class CustomButton {
	
	// Important class variables
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected String text;
    private Font buttonFont;
    private Color primaryBackgroundColour;
    private Color secondaryBackgroundColour = Color.white;
    private Color primaryTextColour = Color.white;
    private Color secondaryTextColour;
    private boolean selectable = true;
    private boolean appearSelected = false;
    
    /**
     * Constructor
     * @param text String text on the button
     * @param x button position x
     * @param y button position y
     * @param width button width
     * @param height button height
     * @param mainColor background colour of the button
     */
    public CustomButton(String text, int x, int y, int width, int height, Color mainColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.primaryBackgroundColour = mainColor;
        this.secondaryTextColour = mainColor;
        this.selectable = true;
        this.appearSelected = false;
        this.buttonFont = getFont("res/KKollektif.ttf", Math.round(this.height * 0.8));

    }
      
    /**
     * Constructor
     * @param text String text on the button
     * @param x button position x
     * @param y button position y
     * @param width button width
     * @param height button height
     */
    public CustomButton(String text, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.primaryBackgroundColour = Color.black;
        this.secondaryTextColour = Color.black;
        this.buttonFont = getFont("res/KKollektif.ttf", Math.round(this.height * 0.8));
    }
    
    /**
     * isMouseOnButton
     * Checks if the mouse is over the button
     * @param panel that the button is on
     * @return true if on and false if not
     * @throws IllegalComponentStateException
     */
    public boolean isMouseOnButton(JPanel panel) throws IllegalComponentStateException{
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        Point relScreenLocation = panel.getLocationOnScreen().getLocation();
        int x = (int) Math.round(mouseLocation.getX() - relScreenLocation.getX());
        int y = (int) Math.round(mouseLocation.getY() - relScreenLocation.getY());

        return ((x >= this.x) && (x <= this.x + this.width) && (y >= this.y) && (y <= this.y + this.height));
    }

    /**
     * draw
     * draws the button
     * @param g
     * @param panel
     */
    public void draw(Graphics g, JPanel panel) {
        g.setColor(primaryTextColour);
        
        // Set colour based on whether it should be selected or not
        if ((isMouseOnButton(panel) && selectable) || appearSelected) {
            g.setColor(secondaryBackgroundColour);
        } else {
            g.setColor(primaryBackgroundColour);
        }
        
        // Fill button rectangle
        if (primaryBackgroundColour != null) {
            g.fillRect(x,y, width, height);
        }

        // Set font and text variables
        g.setFont(buttonFont);
        FontMetrics buttonFontMetrics = g.getFontMetrics(buttonFont);
        int textHeight = buttonFontMetrics.getMaxAscent();
        int textWidth = 0;
        try {
	        textWidth = buttonFontMetrics.stringWidth(text);
        } catch (NullPointerException e) {

        }

        // Set text colour and draw it
        if ((isMouseOnButton(panel) && selectable) || appearSelected) {
            g.setColor(secondaryTextColour);
        } else {
            g.setColor(primaryTextColour);
        }
        g.drawString(text, x + width /2 - textWidth/2, y + height/2 + textHeight/4);

    }

    /**
     * setSelectable
     * set whether the user can select the button
     * @param selectable whether the user can select the button
     */
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
		
	}
	
	/**
	 * changeSelectedAppearance
	 * makes the selected appearance the opposite of what it currently is
	 */
	public void changeSelectedAppearance() {
		if (appearSelected) {
			appearSelected = false;
		} else {
			appearSelected = true;
		}
	}

	/**
	 * setSelected
	 * Set whether the button is really selected
	 * @param selected true if selected and false otherwise
	 */
	public void setSelected(boolean selected) {
		appearSelected = selected;
	}
	
	/**
	 * getFont
	 * returns the font based on variables
	 * @param fileName of the font
	 * @param size of the dont
	 * @return Font object to be drawn
	 */
	public static Font getFont(String fileName, float size) {
		Font font;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(fileName)).deriveFont(size);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.PLAIN, new File(fileName)));
		} catch (IOException | FontFormatException e) {
			font = new Font(Font.SANS_SERIF, Font.PLAIN, Math.round(size)); //if it cannot find the font, defaults to sans-serif of the same size
		}
		return font;
	}

}
