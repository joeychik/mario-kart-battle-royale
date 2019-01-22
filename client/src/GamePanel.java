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



    GamePanel(String mapName, Player player, Game window) {
    	this.window = window;
    	this.player = player;
        this.mapName = mapName;
        mapInfo = new MapReader(mapName);
        map = mapInfo.getMap();
        this.addKeyListener(new MyKeyListener());

        //get rid of this after
        player.setxPos(600);


        //replace placeholders
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //required
        setDoubleBuffered(true);

        this.setFocusable(true);
        this.requestFocus(true);
        this.requestFocusInWindow(true);

        player.update();



        int playerYPos = ((int) player.getyPos()) / 150;
        int playerXPos = ((int) player.getxPos()) / 150;

        String tileName = "";
    	tileName = "road.png";

        for (int i = playerYPos - 4; i < playerYPos + 4; i++) {
            for (int j = playerXPos - 4; j < playerXPos + 4; j++) {
                if ((i > -1) && (j > -1) && (i < map.length) && (j < map[i].length)) {
                    if (map[i][j] != null) {
                        g.setColor(Color.WHITE);
                        if (map[i][j] instanceof Road) {
                        	tileName = "road.png";
                            g.setColor(Color.BLACK);
                        } else if (map[i][j] instanceof Wall) {
                        	tileName = "grass.png";
                            g.setColor(Color.BLUE);
                        }
                        if (!tileName.equals("")) {
                        	g.drawImage(Utilities.getTileImages(tileName), 
                        			
                        			(j - playerXPos + 4) * (int)map[i][j].getDimensions() - player.getRelativeXPosition()%150 - 50, 
                        		   ((i - playerYPos + 4) * (int) map[i][j].getDimensions() - player.getRelativeYPosition()%150 - 150), 
                        		   (int) map[i][j].getDimensions(), (int) map[i][j].getDimensions(), null);
                        	tileName = "road.png";
                        } 
                        
                       	g.setColor(Color.red);
                        
                    }
                }
            }
        }
        
	      

        image = Utilities.getCharacterSpriteImages()[window.characterPanel.getCharacterValue()];
        car = Utilities.getCarImages()[window.carPanel.getCarValue()];

        BufferedImage combined = new BufferedImage(200, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics comb = combined.getGraphics();
        comb.drawImage(car, 0, 0, 40, 60, null);
        comb.drawImage(image, 5, 15, 30, 30, null);

        Graphics2D g2d = (Graphics2D)g;
        AffineTransform trans = AffineTransform.getTranslateInstance(400, 300);

        
        ///System.out.println(window.getPlayerList().get(0).getxPos());
        
        trans.translate(15, 15);
        trans.rotate(player.getOrientation() + Math.PI/2);
        trans.translate(-15, -15);

        g2d.drawImage(combined, trans, this);
        
        
        //g.fillRect(player.getHitBox().x, player.getHitBox().y, player.getHitBox().width, player.getHitBox().height);
        


        //g.drawImage(Utilities.getCharacterSpriteImages()[window.carPanel.getCarValue()], 50 + 100, 100 + 150, 100, 100, null);


        // shows orientation
        //g.drawLine(400,300, (int) (player.getxPos() + 3000 * Math.cos(player.getOrientation())), (int) (300 + 3000 * Math.sin(player.getOrientation())));

//        for (int i = 0; i < 10; i++ ) {
//            if (map[playerYPos -  1][i] instanceof Wall && map[playerYPos -  1][i].getxPosition() > 450) {
//                System.out.println(map[playerYPos - 1][i]);
//                System.out.println(player.getyPos());
//            }
//        }
        
        g.fillRect(400, 300, 5, 5);
        
//        if (map[playerYPos - 1][playerXPos - 1] instanceof Wall) {
//          System.out.println("GANG");
//        }
        
        
        System.out.println(map[playerXPos-1][playerYPos-1]);
        
        
        repaint();
        try {
            Thread.sleep(5);
        } catch (Exception e) {
            System.out.println("idk thread interruption");
        }
    }

    private class MyKeyListener implements KeyListener {
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == 'w') {

                player.setBrake(false);
                player.setAccel(0.15);
            }

            if (e.getKeyChar() == 's') {
                player.setBrake(false);
                player.setAccel(-0.1);
            }

            if (e.getKeyChar() == 'd') {
                player.setOrientation(player.getOrientation() + 0.1);
            }

            if (e.getKeyChar() == 'a') {
                player.setOrientation(player.getOrientation() - 0.1);
            }

        }

        public void keyPressed(KeyEvent e) { }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == 'w' || e.getKeyChar() == 's')
            player.setBrake(true);
        }
    } //end of keyboard listener

}
