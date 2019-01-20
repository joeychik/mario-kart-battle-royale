import javax.imageio.ImageIO; 
import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuPanel extends JPanel {
        private Game2 window;
        private JLayeredPane pane;
        Action action;

        JButton controlScreen;
        JButton joinGame;
        JButton createGame;
        Image background;
        BufferedImage createGameIcon;
        BufferedImage joinGameIcon;
        BufferedImage controlsIcon;


        MenuPanel(Game2 window) {
            this.window = window;

            this.setLayout(new BorderLayout());
            pane = new JLayeredPane();
            pane.setBounds(0, 0, 800, 600);

            try {
                background = ImageIO.read(new File("res/background.png"));
                joinGameIcon = ImageIO.read(new File("res/join game.png"));
                createGameIcon = ImageIO.read(new File("res/create game.png"));
                controlsIcon = ImageIO.read(new File("res/controls.png"));
            } catch (IOException e) {
                System.err.println("Error loading image");
            }

            controlScreen = new JButton(new ImageIcon(controlsIcon.getScaledInstance(300, 75, 0)));
            controlScreen.setBounds((window.getWidth()/2) - 150, (window.getHeight()/2 + 50) , 300, 100);

            joinGame = new JButton(new ImageIcon(joinGameIcon.getScaledInstance(200, 75, 0)));
            joinGame.setBounds(window.getWidth()/2 - 250, window.getHeight()/2 - 50, 250, 100);

            createGame = new JButton(new ImageIcon(createGameIcon.getScaledInstance(200, 75, 0)));
            createGame.setBounds(window.getWidth()/2 + 50, window.getHeight()/2 - 50, 250, 100);

            joinGame.setBorder(BorderFactory.createEmptyBorder());
            joinGame.setContentAreaFilled(false);

            controlScreen.setBorder(BorderFactory.createEmptyBorder());
            controlScreen.setContentAreaFilled(false);

            createGame.setBorder(BorderFactory.createEmptyBorder());
            createGame.setContentAreaFilled(false);

            action = new Action();
            controlScreen.addActionListener(action);
            joinGame.addActionListener(action);
            createGame.addActionListener(action);


            pane.add(controlScreen);
            pane.add(joinGame);
            pane.add(createGame);

            add(pane);

            this.setVisible(true);

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(background, 0,0, 800, 600, null);

            repaint();
        }

        private class Action implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == controlScreen) {
                    window.setControlPanel(new ControlPanel(window));
                    window.changeState(1);
                } else if (e.getSource() == joinGame) {
                    window.changeState(4);
                } else if (e.getSource() == createGame) {
                    window.changeState(3);
                }
            }
        }


}
