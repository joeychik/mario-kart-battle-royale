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

                if ((player.getyPos() - lastPosition >= 150)) {
                    mapIncrement++;
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 9;j++) {
                            visibleMap[i][j] = map[i + mapIncrement][j];
                        }
                    }
                    lastPosition = (int)player.getyPos();
                    player.setRelativePosition(0);
                }

                // this doesn't work
//                if ((player.getyPos() - lastPosition <= -150)) {
//                    mapIncrement++;
//                    for (int i = 0; i < 5; i++) {
//                        for (int j = 0; j < 9;j++) {
//                            visibleMap[i][j] = map[ mapIncrement - i][j];
//                        }
//                    }
//                    lastPosition = (int)player.getyPos();
//                    player.setRelativePosition(0);
//                }

                for (int i = 0; i < visibleMap.length; i++) {
                    for (int j = 0; j < visibleMap[0].length; j++) {
                        if (visibleMap[i][j] instanceof Road) {
                            g.setColor(Color.BLACK);
                            g.fillRect( j * (int)visibleMap[i][j].getDimensions(), (i * (int)visibleMap[i][j].getDimensions() - player.getRelativePosition()), (int) visibleMap[i][j].getDimensions(), (int) visibleMap[i][j].getDimensions());
                        } else if (visibleMap[i][j] instanceof Wall) {
                            g.setColor(Color.BLUE);
                            g.fillRect( j * (int)visibleMap[i][j].getDimensions(), (i * (int)visibleMap[i][j].getDimensions() - player.getRelativePosition()), (int) visibleMap[i][j].getDimensions(), (int) visibleMap[i][j].getDimensions());
                        }
                    }
                }

                g.setColor(Color.RED);
                g.drawRect((int)player.getxPos(),300,player.getDimensions(), player.getDimensions());


                repaint();
            }

        }

        // -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
        private class MyKeyListener implements KeyListener {

            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 'w') {
                    if (e.getKeyChar() == 'd') {
                        player.setxPos(player.getxPos() + player.getVelocity());
                        player.setyPos((player.getyPos() - player.getVelocity()) * -1);
                        player.setRelativePosition(player.getRelativePosition() - (int) player.getVelocity());
                    } else {
                        player.setyPos((player.getyPos() - player.getVelocity()) * -1);
                        player.setRelativePosition(player.getRelativePosition() - (int) player.getVelocity());
                    }
                }

                if (e.getKeyChar() == 's') {
                    if (e.getKeyChar() == 'd') {
                        player.setxPos(player.getxPos() + player.getVelocity());
                        player.setyPos(player.getyPos() + player.getVelocity());
                        player.setRelativePosition(player.getRelativePosition() + (int) player.getVelocity());
                    } else {
                        player.setyPos(player.getyPos() + player.getVelocity());
                        player.setRelativePosition(player.getRelativePosition() + (int) player.getVelocity());
                    }
                }

                if (e.getKeyChar() == 'd') {
                    player.setxPos(player.getxPos() + player.getVelocity());
                }

                if (e.getKeyChar() == 'a') {
                    player.setxPos(player.getxPos() - player.getVelocity());
                }

            }

            public void keyPressed(KeyEvent e) {
                if (player.getVelocity() <= 10) {
                    player.setVelocity(player.getVelocity() + 1);
                }


            }

            public void keyReleased(KeyEvent e) {
                player.setVelocity(0);
            }
        } //end of keyboard listener



    }
