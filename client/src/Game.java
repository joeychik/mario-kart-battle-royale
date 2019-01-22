import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


//Graphics &GUI imports
import javax.swing.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//Keyboard imports


//Util

public class Game extends JFrame {
    private final int UPDATE_RATE = 60; // times the server is updated per second

    //class variables
    JPanel menuPanel;
    JPanel joinGamePanel;
    CharacterPanel characterPanel;
    JPanel serverPanel;
    JPanel controlPanel;
    CarPanel carPanel;
    JPanel createGamePanel;
    GamePanel gamePanel;
    ArrayList<Player> playerList = new ArrayList<Player>();
    int playerID = -1;


    private Player player;
    private Server server = null;
    private ServerConnection serverConnection;
    private Timer gameLoopTimer;
    private boolean inRace = false;

    //Main
    public static void main(String[] args) {
        System.out.println("The game is running.");
        new Game();
    }

    //Constructor - this runs first
    Game() {
        super("MarioKart");

        player = new Player("asdf", 0, 0);

        this.setLocation(0, 0);
        this.setSize(new Dimension(800, 600));
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.menuPanel = new MenuPanel(this);
        this.characterPanel = new CharacterPanel(this);
        this.carPanel = new CarPanel(this);
        this.controlPanel = new ControlPanel(this);
        this.createGamePanel = new CreateGamePanel(this); // NEEDS TO BE CHANGED
        this.joinGamePanel = new JoinGamePanel(this);
        this.gamePanel = new GamePanel("MapOne.txt", player, this);

        changeState(0);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });





        this.setVisible(true);
    } //End of Constructor

    public void startRace() {
        gameLoopTimer = new Timer();
        gameLoopTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                serverConnection.send(new ClientPacket(
                        player.getVelocity(),
                        player.getAccel(),
                        player.getOrientation(),
                        player.isBrake()
                ));
            }
        }, 0, 1000 / UPDATE_RATE);
    }

    public Server getServer() {
        return server;
    }

    public void ready() {
        serverConnection.send(new ClientPacket(0, 0, 0.5 * Math.PI, true));
    }

    public Server startServer() {
        server = new Server("MapOne.txt", 3, null, 3);
        return server;
    }

    public void connectToGame(String serverIP, int port) {
        serverConnection = new ServerConnection(serverIP, port);
    }

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
            case 6:
                switchPanel(createGamePanel);
                return;
            case 7:
                switchPanel(gamePanel);
                return;
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    public ArrayList<Player> getPlayerList() {
    	return playerList;
    }

    private void switchPanel(JPanel newPanel) {
        getContentPane().removeAll();
        newPanel.setPreferredSize(new Dimension(800, 600));
        getContentPane().add(newPanel, BorderLayout.EAST);
        newPanel.setVisible(true);

        getContentPane().revalidate();
        getContentPane().repaint();
    }

    private void closeWindow() {
        dispose();
    }

    public void setMenuPanel(JPanel menuPanel) {
        this.menuPanel = menuPanel;
    }

    public void setJoinGamePanel(JPanel joinGamePanel) {
        this.joinGamePanel = joinGamePanel;
    }

    public void setCharacterPanel(CharacterPanel characterPanel) {
        this.characterPanel = characterPanel;
    }

    public void setServerPanel(JPanel serverPanel) {
        this.serverPanel = serverPanel;
    }

    public void setControlPanel(JPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    /** --------- INNER CLASSES ------------- **/

    // private class for handling connection with server
    private class ServerConnection implements Runnable {
        private String serverIP;
        private int port;
        private Socket socket;
        private JsonReader input;
        private JsonWriter output;
        private Gson gson;
        private boolean receiving = true;

        public ServerConnection(String serverIP, int port) {
            this.serverIP = serverIP;
            this.port = port;
            this.gson = new Gson();

            try {
                socket = new Socket(serverIP, port);
                input = new JsonReader(new InputStreamReader(socket.getInputStream()));
                output = new JsonWriter(new OutputStreamWriter(socket.getOutputStream()));

                System.out.println("connected");
                StartClientPacket packet = new StartClientPacket(player);
                gson.toJson(packet, StartClientPacket.class, output);
                try {
                    output.flush();
                } catch (IOException e) {
                    System.err.println("couldn't send message");
                    e.printStackTrace();
                }

                Thread thread = new Thread(this);
                thread.start();
            } catch (Exception e) {
                System.err.println("problem connecting to server");
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (receiving) {
                try {
                    ServerPacket packet = gson.fromJson(input, ServerPacket.class);
                    if (packet.getPacketType() == ServerPacket.START_PACKET) {
                        processStartServerPacket(packet);
                    } else if (packet.getPacketType() == ServerPacket.GAME_PACKET) {
                        processServerPacket(packet);
                    }
                } catch (JsonSyntaxException | JsonIOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void send(ClientPacket packet) {
            gson.toJson(packet, ClientPacket.class, output);
            try {
                output.flush();
            } catch (IOException e) {
                System.err.println("couldn't send message");
                e.printStackTrace();
            }
        }

        private void processStartServerPacket(ServerPacket packet) {

        	playerList = packet.getPlayerList();
        	if (playerID == -1) {
        	    playerID = packet.getPlayerList().size();
                inRace = false;
            }
        }

        private void processServerPacket(ServerPacket packet) {
            inRace = true;
            playerList = packet.getPlayerList();
        }
    }

}
