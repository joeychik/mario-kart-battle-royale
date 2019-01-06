import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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


    class Game extends JFrame {

        //class variables
        static JFrame window;
        JPanel gamePanel;
        Player player;

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
            this.setSize(800, 600);
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

            GameAreaPanel (String mapName) {
                this.mapName = mapName;
                mapInfo = new MapReader(mapName);
                map = mapInfo.getMap();
            }

            public void paintComponent(Graphics g) {
                super.paintComponent(g); //required
                setDoubleBuffered(true);

                for (int i = 0; i < map.length; i++) {
                    for (int j = 0; j < map[0].length; j++) {
                        if (map[i][j] instanceof Road) {
                            g.setColor(Color.BLACK);
                            g.fillRect( i * (int)map[i][j].getDimensions(), j * (int)map[i][j].getDimensions(), (int) map[i][j].getDimensions(), (int) map[i][j].getDimensions());
                        } else if (map[i][j] instanceof Wall) {
                            g.setColor(Color.BLUE);
                            g.fillRect( i * (int)map[i][j].getDimensions(), j * (int)map[i][j].getDimensions(), (int) map[i][j].getDimensions(), (int) map[i][j].getDimensions());
                        }
                    }
                }

                g.setColor(Color.RED);
                g.drawRect((int)player.getxPos(), (int)player.getyPos(), player.getDimensions(), player.getDimensions());


                repaint();
            }

        }

        // -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
        private class MyKeyListener implements KeyListener {

            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (player.getVelocity() <= 10) {
                    player.setVelocity(player.getVelocity() + 1);
                }

                if (e.getKeyChar() == 'w') {
                    player.setyPos(player.getyPos() - player.getVelocity());
                }

                if (e.getKeyChar() == 's') {
                    player.setyPos(player.getyPos() + player.getVelocity());
                }

                if (e.getKeyChar() == 'd') {
                    player.setxPos(player.getxPos() + player.getVelocity());
                }

                if (e.getKeyChar() == 'a') {
                    player.setxPos(player.getxPos() - player.getVelocity());
                }

            }

            public void keyReleased(KeyEvent e) {
            }
        } //end of keyboard listener



    }
