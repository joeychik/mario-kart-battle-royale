 import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class PanelTester {
    public static void main (String[] args) {
        Game2 game = new Game2();
    }
}

    /**
     * Main display hub for the program
     * All panels stem from this one
     */
    public class Game2 extends JFrame {

        private JPanel menuPanel;
        private JPanel joinGamePanel;
        private  JPanel characterPanel;
        private JPanel serverPanel;
        private JPanel controlPanel;
        private JPanel carPanel;
        
        public Game2() {
            super("MarioKart");

            this.setLocation(0, 0);
            this.setSize(new Dimension(800, 600));
            this.setResizable(false);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setLayout(new BorderLayout());



//            this.dashboardPanel = new DashBoardPanel(this);
//
//
//            //generate panels
//            this.loginPanel = new LoginPanel(this);
//            this.pmPanel = new PMPanel(this);
            this.menuPanel = new MenuPanel(this);
            this.characterPanel = new CharacterPanel(this);
            this.carPanel = new CarPanel(this);



            //set displayed panel to menu
            changeState(0);

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    closeWindow();
                }
            });
            
            
            
            

            this.setVisible(true);
        }
        /**
         * 0 = login, 1 = pm, 2 = allChat
         */
        public void changeState(int state) {
            switch (state) {
                case 0:
                    switchPanel(menuPanel);
                    return;
                case 1:
                    switchPanel(controlPanel);
                    return;
                case 2:
                    switchPanel(joinGamePanel);
                    return;
                case 3:
                    switchPanel(serverPanel);
                    return;
                case 4:
                    switchPanel(characterPanel);
                    return;
                case 5:
                	switchPanel(carPanel);
                	return;
                default:
                    throw new IndexOutOfBoundsException();
            }
        }


        private void closeWindow() {
            dispose();
        }

        private void switchPanel(JPanel newPanel) {
            getContentPane().removeAll();
            newPanel.setPreferredSize(new Dimension(800, 600));
            getContentPane().add(newPanel, BorderLayout.EAST);
            newPanel.setVisible(true);

            getContentPane().revalidate();
            getContentPane().repaint();
        }

        public void setMenuPanel(JPanel menuPanel) {
            this.menuPanel = menuPanel;
        }

        public void setJoinGamePanel(JPanel joinGamePanel) {
            this.joinGamePanel = joinGamePanel;
        }

        public void setCharacterPanel(JPanel characterPanel) {
            this.characterPanel = characterPanel;
        }

        public void setServerPanel(JPanel serverPanel) {
            this.serverPanel = serverPanel;
        }

        public void setControlPanel(JPanel controlPanel) {
            this.controlPanel = controlPanel;
        }
    }
