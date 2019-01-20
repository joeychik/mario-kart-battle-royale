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

public class ControlPanel extends JPanel {
    private Game2 window;
    private JLayeredPane pane;
    Action action;
    BufferedImage menuIcon;

    JButton menuScreen;
    Image background;


    ControlPanel(Game2 window) {
        this.window = window;

        this.setLayout(new BorderLayout());
        pane = new JLayeredPane();
        pane.setBounds(0, 0, 800, 600);

        try {
            background = ImageIO.read(new File("res/Control Page.png"));
            menuIcon = ImageIO.read(new File("res/menu.png"));
        } catch (IOException e) {
            System.err.println("Error loading image");
        }


        menuScreen = new JButton(new ImageIcon(menuIcon.getScaledInstance(200, 50, 0)));
        menuScreen.setBounds(window.getWidth() - 250, 50, 200, 50);
        menuScreen.setBorder(BorderFactory.createEmptyBorder());
        menuScreen.setContentAreaFilled(false);

        action = new Action();
        menuScreen.addActionListener(action);

        add(pane);
        pane.add(menuScreen);
        this.setVisible(true);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0,-50, 800, 650, null);

        repaint();
    }

    private class Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == menuScreen) {
                window.changeState(0);
            }
        }
    }


}
