import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Utilities {

	/**
	 * getCharacterImages
	 * @return Image[]
	 */
	public static Image[] getCharacterImages() {

		Image[] images = new Image[3];
		try {
			//reads player images
			images[0] = ImageIO.read(new File("res/pipecard.png"));
			images[1] = ImageIO.read(new File("res/brickCard.png"));
			images[2] = ImageIO.read(new File("res/coincard.png"));
		} catch (IOException e) {
			System.err.println("Error loading image");
		}
		return images;
	}

	/**
	 * getCarImages
	 * @return Image[]
	 */
	public static Image[] getCarImages() {

		Image[] images = new Image[3];
		try {
			//loads car images
			images[0] = ImageIO.read(new File("res/car1.png"));
			images[1] = ImageIO.read(new File("res/car2.png"));
			images[2] = ImageIO.read(new File("res/car3.png"));
		} catch (IOException e) {
			System.err.println("Error loading image");
		}
		return images;
	}

	/**
	 * getCharacterSpriteImages
	 * @return Image[]
	 */
	public static Image[] getCharacterSpriteImages() {

		Image[] images = new Image[3];
		try {
			//loads character sprites
			images[0] = ImageIO.read(new File("res/pipe.png"));
			images[1] = ImageIO.read(new File("res/brick.png"));
			images[2] = ImageIO.read(new File("res/coin.png"));
		} catch (IOException e) {
			System.err.println("Error loading image");
		}
		return images;
	}

	/**
	 * getTileImages
	 * @param name
	 * @return Image
	 */
	public static Image getTileImages(String name) {
		Image im = null;
		try {
			 //loads specified image
			 im = ImageIO.read(new File("res/" + name));
		} catch (IOException e) {
			System.err.println("Error loading image");
		}
		
		return im;
	}
	
	public static Image getIcon(String name) {
		Image im = null;
		try {
			 
			 im = ImageIO.read(new File("res/" + name));
		} catch (IOException e) {
			System.err.println("Error loading image");
		}
		
		return im;
	}
}
