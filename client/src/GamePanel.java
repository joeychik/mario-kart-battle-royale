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
    private Game2 window;

    private Image image;
    private Image car;

    AffineTransform identity = new AffineTransform(0, 0, 0, 0, 0, 0);



    GamePanel(String mapName, Player player, Game2 window) {
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

        for (int i = playerYPos - 3; i < playerYPos + 3; i++) {
            for (int j = playerXPos - 3; j < playerXPos + 3; j++) {
                if ((i > -1) && (j > -1) && (i < map.length) && (j < map[i].length)) {
                    if (map[i][j] != null) {
                        g.setColor(Color.WHITE);
                        if (map[i][j] instanceof Road) {
                            g.setColor(Color.BLACK);
                        } else if (map[i][j] instanceof Wall) {
                            g.setColor(Color.BLUE);
                        }

                        g.fillRect((j - playerXPos + 3) * (int)map[i][j].getDimensions() - ((int)(player.getxPos()) % 150 % 150), ((i - playerYPos + 3) * (int) map[i][j].getDimensions() - player.getRelativePosition() % 150), (int) map[i][j].getDimensions(), (int) map[i][j].getDimensions());
                    }
                }
            }
        }

        image = Utilities.getCharacterSpriteImages()[window.characterPanel.getCharacterValue()];
        car = Utilities.getCarImages()[window.carPanel.getCarValue()];

        BufferedImage combined = new BufferedImage(200, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics comb = combined.getGraphics();
        comb.drawImage(car, 0, 0, null);
        comb.drawImage(image, 50, 100, null);

        g.setColor(Color.RED);
        g.drawRect(400,300,player.getDimensions(), player.getDimensions());


        Graphics2D g2d = (Graphics2D)g;
        AffineTransform trans = AffineTransform.getTranslateInstance(400, car.getWidth(null)/2);

        trans.translate(100, 150);
        trans.rotate(player.getOrientation() + Math.PI/2);
        trans.translate(-100, -150);

        //trans.translate((int)player.getxPos()-250, 0);

        g2d.drawImage(combined, trans, this);

        //g.drawImage(Utilities.getCharacterSpriteImages()[window.carPanel.getCarValue()], 50 + 100, 100 + 150, 100, 100, null);


        // shows orientation
        g.drawLine(400,300, (int) (player.getxPos() + 3000 * Math.cos(player.getOrientation())), (int) (300 + 3000 * Math.sin(player.getOrientation())));

        for (int i = 0; i < 10; i++ ) {
            if (map[playerYPos -  1][i] instanceof Wall && map[playerYPos -  1][i].getxPosition() > 450) {
                System.out.println(map[playerYPos][i]);
                System.out.println(player.getyPos());
            }
        }
//        for (int i = 0; i < map[0].length; i++) {
//            if (map[playerYPos][i].getHitBox().intersects(player.getHitBox()) && !map[playerYPos][i].getDriveable() ) {
//                System.out.println("hit");
//                if (player.getxPos() > map[playerYPos][i].getxPosition()) {
//                    System.out.println("right");
//                    player.setxPos(map[playerYPos][i].getxPosition() + 151);
//                    player.setOrientation(Math.PI - player.getOrientation());
//                    player.setAccel(-0.1);
//                } else if (player.getxPos() < map[playerYPos][i].getxPosition() + 150) {
//                    System.out.println("left");
//                    player.setxPos(map[playerYPos][i].getxPosition() - 1);
//                    player.setOrientation(Math.PI - player.getOrientation());
//                    player.setAccel(-0.1);
//                }
//                //player.setxPos(map[playerYPos][i].getxPosition());
//                //player.setyPos(map[playerYPos][i].getHitBox().getY() + (player.getyPos() % 150) + 1);
//            }
//        }
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
