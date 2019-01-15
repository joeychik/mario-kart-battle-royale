import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


//Graphics &GUI imports
import javax.swing.JFrame;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

//Keyboard imports


//Util

class Game extends JFrame {
    //class variables
    static JFrame window;
    JPanel gamePanel;
    Player player;
    boolean movingBackwards = false;

    //Main
    public static void main(String[] args) {
        System.out.println("The game is running.");
        window = new Game();

    }

    //Constructor - this runs first
    Game() {
        super("Mario Kart");


        // Set the frame to full screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1350, 600);
        // this.setUndecorated(true);  //Set to true to remove title bar
        // frame.setResizable(false);



        //Set up the game panel (where we put our graphics)
        gamePanel = new GameAreaPanel("MapOne"); //placeholder
        this.add(new GameAreaPanel("MapOne"));

        MyKeyListener keyListener = new MyKeyListener();
        this.addKeyListener(keyListener);
        player = new Player();
        this.requestFocusInWindow(); //make sure the frame has focus
        this.setVisible(true);




    } //End of Constructor



    /** --------- INNER CLASSES ------------- **/

    // Inner class for the the game area - This is where all the drawing of the screen occurs
    private class GameAreaPanel extends JPanel {
        String mapName;
        MapReader mapInfo;
        MapComponent[][] map;
        int lastPosition = 150;
        int mapIncrement = 0;
        int yMapPosition = 0;


        GameAreaPanel (String mapName) {
            this.mapName = mapName;
            mapInfo = new MapReader(mapName);
            map = mapInfo.getMap();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g); //required
            setDoubleBuffered(true);

            player.update();

            int playerYPos = ((int) player.getyPos()) / 150;

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

            repaint();
            try {
                Thread.sleep(5);
            } catch (Exception e) {
                System.out.println("idk thread interruption");
            }
        }

    }

    // -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
    private class MyKeyListener implements KeyListener {
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == 'w') {
                player.setBrake(false);
                player.setAccel(0.45);
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
