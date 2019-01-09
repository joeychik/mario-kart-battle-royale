import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLOutput;
import java.util.ArrayList;


//Graphics &GUI imports
import javax.swing.JFrame;

import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


//Util
import java.util.ArrayList;
import java.util.Arrays;


class GameTest extends JFrame {
    //class variables
    static JFrame window;
    JPanel gamePanel;
    Player player;
    boolean movingBackwards = false;

    //Main
    public static void main(String[] args) {
        System.out.println("The game is running.");
        window = new GameTest();

    }

    //Constructor - this runs first
    GameTest() {
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
        MapComponent[][] visibleMap = new MapComponent[5][9];
        int lastPosition = 150;
        int mapIncrement = 0;
        int yMapPosition = 0;


        GameAreaPanel (String mapName) {
            this.mapName = mapName;
            mapInfo = new MapReader(mapName);
            map = mapInfo.getMap();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 9; j++) {
                    visibleMap[i][j] = map[i][j];
                }
            }
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g); //required
            setDoubleBuffered(true);

            player.update();

            if ((player.getyPos() - lastPosition >= 150)) {
                mapIncrement++;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 9;j++) {
                        visibleMap[i][j] = map[i + mapIncrement][j];
                    }
                }
                lastPosition = (int)player.getyPos();
                player.setPosRelativePosition(0);
            }

            if ((player.getyPos() - lastPosition <= -150)) {
                mapIncrement--;
                System.out.println("incremented");
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 9;j++) {
                        visibleMap[i][j] = map[i + mapIncrement][j];
                    }
                }
                lastPosition = (int)player.getyPos();
                player.setNegRelativePosition(0);
            }

            for (int i = 0; i < visibleMap.length; i++) {
                for (int j = 0; j < visibleMap[0].length; j++) {
                    if (visibleMap[i][j] instanceof Road) {
                        g.setColor(Color.BLACK);
                        if (player.isMovingBackwards()) {
                            //System.out.println(player.getOrientation());
                            g.fillRect(j * (int) visibleMap[i][j].getDimensions(), (((i * (int) visibleMap[i][j].getDimensions()) - 150)
                                    + player.getNegRelativePosition()), (int) visibleMap[i][j].getDimensions(), (int) visibleMap[i][j].getDimensions());
                        } else {
                            g.fillRect(j * (int) visibleMap[i][j].getDimensions(), (i * (int) visibleMap[i][j].getDimensions()
                                    - player.getPosRelativePosition()), (int) visibleMap[i][j].getDimensions(), (int) visibleMap[i][j].getDimensions());
                        }
                    } else if (visibleMap[i][j] instanceof Wall) {
                        g.setColor(Color.BLUE);
                        if (player.isMovingBackwards()) {
                            g.fillRect(j * (int) visibleMap[i][j].getDimensions(), (((i * (int) visibleMap[i][j].getDimensions()) - 150)
                                    + player.getNegRelativePosition()), (int) visibleMap[i][j].getDimensions(), (int) visibleMap[i][j].getDimensions());
                        } else {
                            g.fillRect(j * (int) visibleMap[i][j].getDimensions(), (i * (int) visibleMap[i][j].getDimensions()
                                    - player.getPosRelativePosition()), (int) visibleMap[i][j].getDimensions(), (int) visibleMap[i][j].getDimensions());
                        }
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
                player.setAccel(0.25);
            }

            if (e.getKeyChar() == 's') {
                player.setBrake(false);
                player.setAccel(-0.05);
            }

            if (e.getKeyChar() == 'd') {
                player.setOrientation(player.getOrientation() - 0.1);
            }

            if (e.getKeyChar() == 'a') {
                player.setOrientation(player.getOrientation() + 0.1);
            }

        }

        public void keyPressed(KeyEvent e) { }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == 'w' || e.getKeyChar() == 's')
            player.setBrake(true);
        }
    } //end of keyboard listener



}
