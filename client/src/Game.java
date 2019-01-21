import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


//Graphics &GUI imports
import javax.swing.*;

import java.awt.Graphics;
import java.awt.Color;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

//Keyboard imports


//Util

public class Game extends JFrame {
    //class variables
    private static JFrame window;
    private JPanel gamePanel;
    private Player player;
    private ServerConnection serverConnection;

    //Main
    public static void main(String[] args) {
        System.out.println("The game is running.");
        window = new Game();

    }

    //Constructor - this runs first
    Game() {
        serverConnection = new ServerConnection("127.0.0.1", 5000);
//        super("Mario Kart");
//
//        // Set the frame to full screen
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setSize(1350, 600);
//        // this.setUndecorated(true);  //Set to true to remove title bar
//        // frame.setResizable(false);
//
//        //Set up the game panel (where we put our graphics)
//        gamePanel = new GamePanel("MapOne"); //placeholder
//        this.add(gamePanel);
//
//        MyKeyListener keyListener = new MyKeyListener();
//        this.addKeyListener(keyListener);
//        this.requestFocusInWindow(); //make sure the frame has focus
//        this.setVisible(true);
    } //End of Constructor



    /** --------- INNER CLASSES ------------- **/

    // private class for handling connection with server
    private class ServerConnection implements Runnable {
        private String serverIP;
        private int port;
        private Socket socket;
        private JsonReader input;
        private JsonWriter output;
        private Gson gson;
        private boolean recieving = true;

        public ServerConnection(String serverIP, int port) {
            this.serverIP = serverIP;
            this.port = port;
            this.gson = new Gson();

            try {
                socket = new Socket(serverIP, port);
                input = new JsonReader(new InputStreamReader(socket.getInputStream()));
                output = new JsonWriter(new OutputStreamWriter(socket.getOutputStream()));

                System.out.println("connected");

                StartClientPacket startClientPacket = new StartClientPacket("asdf", "", "");

                gson.toJson(startClientPacket, StartClientPacket.class, output);
                output.flush();

                run();
            } catch (Exception e) {
                System.err.println("problem connecting to server");
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (recieving) {
                try {
                    StartServerPacket startServerPacket = gson.fromJson(input, StartServerPacket.class);
                    //System.out.println(startServerPacket.getPlayerList().get(0).getName());
                } catch (JsonSyntaxException | JsonIOException e) {
                    e.printStackTrace();
                }
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
