import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {

    private String mapName;
    private MapReader mapInfo;
    private MapComponent[][] map;
    private int lastPosition = 150;
    private int mapIncrement = 0;
    private int yMapPosition = 0;
    private Player player;
    private Game window;

    private Image image;
    private Image car;

    AffineTransform identity = new AffineTransform(0, 0, 0, 0, 0, 0);

    BufferedImage combined;
    
    int yArrayPosition;
    int xArrayPosition;
    private MapComponent bufferMarker = null;


    GamePanel(String mapName, Player player, Game window) {
    	this.window = window;
    	this.player = player;
        this.mapName = mapName;
        mapInfo = new MapReader(mapName);
        map = mapInfo.getMap();
        this.addKeyListener(new MyKeyListener());

        player.update();
        
        //get rid of this after
        player.setxPos(700);


        
        
        
        
        for (MapComponent marker: mapInfo.getMarkerList()) {
            player.getMarkerList().add(marker);
        }

        //replace placeholders
    }

    public void paintComponent(Graphics g) {
    	
    	// Required paintComponent methods
        super.paintComponent(g);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.requestFocus(true);
        this.requestFocusInWindow(true);
        
//        if (window.getCharVal() != -1 && window.getCarVal() != -1) {
//            image = Utilities.getCharacterSpriteImages()[window.getCharVal()];
//            car = Utilities.getCarImages()[window.getCarVal()];
//            combined = combineSprites(image, car);
//        }
        
        if (window.characterPanel.getCharacterValue() != -1 && window.carPanel.getCarValue() != -1) {
            image = Utilities.getCharacterSpriteImages()[window.characterPanel.getCharacterValue()];
            car = Utilities.getCarImages()[window.carPanel.getCarValue()];
            combined = combineSprites(image, car);
        }
        
        
        // Required for rotation of sprites
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform trans = AffineTransform.getTranslateInstance(400, 300);

        // Update player position, velocity, etc.
        //player.update();
        
        // Draw the entire map in desired location
        drawMap(g);
        

//        for(Player p: window.getPlayerList()) {    	
//        	if (p.getPlayerID() == window.getGameId()) {
//        		System.out.println(p.getxPos());
//        		System.out.println(p.getyPos());
//        	}
//        }
        

        // Required to rotate sprites in place
        trans.translate(15, 15);
        trans.rotate(player.getOrientation() + Math.PI/2);
        trans.translate(-15, -15);
        trans.translate(-5, 0);

        
        // Draw the actual combined sprites
        g2d.drawImage(combined, trans, this);
                
        
        
//        System.out.println(window.getPlayerList().get(0).compareTo(window.getPlayerList().get(1)));

        for(Player p: window.getPlayerList()) {    	

        	if (window.getGameId() != p.getPlayerID()) {

                g.drawImage(Utilities.getIcon("fireflower.png"), (int)(p.getxPos() - player.getxPos()) + 400 - 5, (int)(p.getyPos() - player.getyPos()) + 300 - 15, 30, 30, null);

        		//g.drawImage(Utilities.getIcon("fireflower.png"), (int)(p.getxPos() - player.getxPos()) + 400, (int)(p.getyPos() - player.getyPos()) + 300, 200, 300, null);
        	}
            //g.drawRect((int)(p.getxPos() - player.getxPos()) + 400, (int)(p.getyPos() - player.getyPos()) + 300, 40, 60);
        }
        
        collide();
        



            
        // Call again
        repaint();
        
//        try {
//            Thread.sleep(1);
//        } catch (Exception e) {
//            System.out.println("idk thread interruption");
//        }
    }

    private void collide() {
    	//player position corresponding to an index in the array
        yArrayPosition = ((int)player.getyPos() /150) - 1;
        xArrayPosition = ((int)player.getxPos()/150) - 1;

        //markers are used to determine how far the player has travelled
        //the number of markers passed is used to determine a player's position in the leaderboard

        // checks if the player is over a marker
        if (map[yArrayPosition][xArrayPosition] instanceof Marker) {
            //checks if the player is currently intersecting any remaining markers
            for (MapComponent check: player.getMarkerList()) {

                //if they are, player's markersPassed increments by one
                if (player.getHitBox().intersects(check.getHitBox())) {
              
                    player.setMarkersPassed(player.getMarkersPassed() + 1);

                    //buffer to get rid of concurrent modification error
                    bufferMarker = check;
                    //removes intersecting marker from arraylist of remaining markers

                }
            }
            //checks if player is over a finish line
        } else if (map[yArrayPosition][xArrayPosition] instanceof FinishMarker) {
            //increase laps completed by one
            player.setLapsCompleted(player.getLapsCompleted() + 1);
            if (player.getLapsCompleted() == 3) {
                player.setFinishedRace(true);
            }
            //re initializes player's arraylist of markers
            for (MapComponent marker: mapInfo.getMarkerList()) {
            	player.setMarkersPassed(player.getMarkersPassed()+1);
                player.getMarkerList().add(marker);
            }
        }

    if (bufferMarker != null) {
        player.getMarkerList().remove(bufferMarker);
    }		
	}

	private BufferedImage combineSprites(Image image2, Image car2) {
        BufferedImage combined = new BufferedImage(200, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics comb = combined.getGraphics();
        comb.drawImage(car, 0, 0, 40, 60, null);
        comb.drawImage(image, 5, 15, 30, 30, null);
        return combined;
	}

	private void drawMap(Graphics g) {
    	
        int playerYPos =  (int)(window.getPlayer().getyPos() / 150);
        int playerXPos =  (int)(window.getPlayer().getxPos() / 150);
    	
//        for(Player p: window.getPlayerList()) {    	
//        	if (p.getPlayerID() == window.getGameId()) {
//                playerYPos =  (int)(p.getyPos() / 150);
//                playerXPos =  (int)(p.getxPos() / 150);
//        	}
//        }
        

        
        String tileName = "";
    	tileName = "road.png";
        for (int i = playerYPos - 3; i < playerYPos + 3; i++) {
            for (int j = playerXPos - 4; j < playerXPos + 4; j++) {
                if ((i > -1) && (j > -1) && (i < map.length) && (j < map[i].length)) {
                    if (map[i][j] != null) {

                    	if (map[i][j] instanceof Road) {
                        	tileName = "road.png";
                        } else if (map[i][j] instanceof Wall) {
                        	tileName = "grass.png";
                        } else if (map[i][j] instanceof Marker) {
                        	tileName = "road.png";
                        } else if (map[i][j] instanceof FinishMarker) {
                        	tileName = "finish.png";
                        }
                    	                    	
                        if (!tileName.equals("")) {
                        	g.drawImage(Utilities.getTileImages(tileName), 
                        			
                        			(j - playerXPos + 4) * (int)map[i][j].getDimensions() - player.getRelativeXPosition()%150 - 50,
                        		   ((i - playerYPos + 3) * (int) map[i][j].getDimensions() - player.getRelativeYPosition()%150),
                        		   (int) map[i][j].getDimensions(), (int) map[i][j].getDimensions(), null);
                        	tileName = "finish.png";
                        } 
                        
                       	g.setColor(Color.red);
                        
                    }
                }
            }
        }
        
        if (map[playerYPos - 1][playerXPos - 1] instanceof Wall) {
        	player.setVelocity(0);
        	player.setAccel(0);
        }
	}

	private class MyKeyListener implements KeyListener {
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == 'w') {

                player.setBrake(false);
                player.setAccel(0.3);
            }else if (e.getKeyChar() == 's') {
                player.setBrake(false);
                player.setAccel(-0.2);
            }else if (e.getKeyChar() == 'd') {
                player.setOrientation(player.getOrientation() + 0.2);
            } else if (e.getKeyChar() == 'a') {
                player.setOrientation(player.getOrientation() - 0.2);
            } else {
                player.setAccel(-0.1);

            }
            
            

        }

        public void keyPressed(KeyEvent e) { }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == 'w' || e.getKeyChar() == 's')
            player.setBrake(true);
        }
    } //end of keyboard listener

}
