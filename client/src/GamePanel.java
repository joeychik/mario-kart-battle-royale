import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel {
    private String mapName;
    private MapReader mapInfo;
    private MapComponent[][] map;
    private int lastPosition = 150;
    private int mapIncrement = 0;
    private int yMapPosition = 0;
    private Player player;

    GamePanel(String mapName, Player player) {
    	this.player = player;
        this.mapName = mapName;
        mapInfo = new MapReader(mapName);
        map = mapInfo.getMap();
        this.addKeyListener(new MyKeyListener());

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
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != null) {
                    g.setColor(Color.WHITE);
                    if (map[i][j] instanceof Road) {
                        g.setColor(Color.BLACK);
                    } else if (map[i][j] instanceof Wall) {
                        g.setColor(Color.BLUE);
                    }
                    g.fillRect(j * (int) map[i][j].getDimensions(), ((i - playerYPos + 3) * (int) map[i][j].getDimensions() - player.getRelativePosition() % 150), (int) map[i][j].getDimensions(), (int) map[i][j].getDimensions());
                }
            }
        }

        g.setColor(Color.RED);
        g.drawRect((int)player.getxPos(),300,player.getDimensions(), player.getDimensions());

        // shows orientation
        g.drawLine((int)player.getxPos(),300, (int) (player.getxPos() + 3000 * Math.cos(player.getOrientation())), (int) (300 + 3000 * Math.sin(player.getOrientation())));


        for (int i = 0; i < 9; i++) {
            if (map[playerYPos][i].getHitBox().intersects(player.getHitBox()) && !map[playerYPos][i].getDriveable() ) {
                if (player.getxPos() > map[playerYPos][i].getxPosition()) {

                    player.setxPos(map[playerYPos][i].getxPosition() + 151);
                    player.setOrientation(Math.PI - player.getOrientation());
                    player.setAccel(-0.1);
                } else if (player.getxPos() < map[playerYPos][i].getxPosition()) {
                    player.setxPos(map[playerYPos][i].getxPosition() - 1);
                    player.setOrientation(Math.PI - player.getOrientation());
                    player.setAccel(-0.1);
                }
                //player.setxPos(map[playerYPos][i].getxPosition());
                player.setyPos(map[playerYPos][i].getHitBox().getY() + (player.getyPos() % 150) + 1);
            }
        }
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