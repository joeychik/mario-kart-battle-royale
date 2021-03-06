/**
 * [Game.java]  
 * Actual game code class
 * @author Yash Arora
 * @since 2019-01-22
 */

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
    private final int UPDATE_RATE = 30; // times the server is updated per second

    //class variables
    private JPanel menuPanel;
    private JPanel joinGamePanel;
    private CharacterPanel characterPanel;
    private JPanel serverPanel;
    private JPanel controlPanel;
    private CarPanel carPanel;
    private JPanel createGamePanel;
    private GamePanel gamePanel;
    private ArrayList<Player> playerList = new ArrayList<Player>();
    private int playerID = -1;


    private Player player;
    private Server server = null;
    private ServerConnection serverConnection;
    private Timer gameLoopTimer;
    private boolean inRace = false;

    private int charVal = -1;
	private int carVal = -1;

    public int getCharVal() {
		return charVal;
	}

	public int getCarVal() {
		return carVal;
	}


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
        this.setCharacterPanel(new CharacterPanel(this));
        this.setCarPanel(new CarPanel(this));
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

    /**
     * startRace()
     * starts the race, sends info to server
     */
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

    /**
     * ready()
     * communicates ready status to server. when all the players in the lobby are ready, the server starts the game
     */
    public void ready() {
        serverConnection.send(new ClientPacket(0, 0, 0.5 * Math.PI, true));
    }

    /**
     * startServer()
     * starts a new server
     * @return the server created
     */
    public Server startServer() {
        server = new Server("MapOne.txt", 3, null, 3);
        return server;
    }

    /**
     * connectToGame()
     * connects to an existing server
     * @param serverIP ip address of the server
     * @param port port number of the server
     */
    public void connectToGame(String serverIP, int port) {
        serverConnection = new ServerConnection(serverIP, port);
    }

    /**
     * changeState()
     * changes the state of the JFrame
     * 0 is menuPanel
     * 1 is controlPanel
     * 2 joinGamePanel
     * 3 serverPanel
     * 4 characterPanel
     * 5 carPanel
     * 6 createGamePanel
     * 7 gamePanel
     * @param state which panel to switch to
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
                switchPanel(getCharacterPanel());
                return;
            case 5:
                switchPanel(getCarPanel());
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

    /**
     * returns the playerList
     * @return the list of players in the game
     */
    public ArrayList<Player> getPlayerList() {
    	return playerList;
    }

    /**
     * returns the player for this game
     * @return player
     */
    public Player getPlayer() {
    	return player;
    }

    /**
     * switches the Jpanel on the JFrame
     * @param newPanel
     */
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

    public int getGameId() {
    	return playerID;
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
            this.gson = new Gson(); // used to serialize/deserialize json objects to/from java objects

            try {
                socket = new Socket(serverIP, port);
                input = new JsonReader(new InputStreamReader(socket.getInputStream()));
                output = new JsonWriter(new OutputStreamWriter(socket.getOutputStream()));

                System.out.println("connected");
                // send setup packet to server
                StartClientPacket packet = new StartClientPacket(player);
                gson.toJson(packet, StartClientPacket.class, output);
                try {
                    output.flush();
                } catch (IOException e) {
                    System.err.println("couldn't send message");
                    e.printStackTrace();
                }

                Thread thread = new Thread(this);
                thread.start(); // starts the thread that receives packets from the server
            } catch (Exception e) {
                System.err.println("problem connecting to server");
                e.printStackTrace();
            }
        }

        @Override
        public void run() { // receives the packets from server
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
                    System.exit(-1);
                }
            }
        }

        // send packet to server
        public synchronized void send(ClientPacket packet) { // synchronized to prevent overlapping timer tasks
           // System.out.println(packet.getAccel() + ", " + packet.getOrientation());

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
            if (!inRace) {
                changeState(7);
                inRace = true;
            }
            startRace();
            playerList = packet.getPlayerList();
            for (Player p : playerList) {
                if (p.getPlayerID() == playerID) {



                    player.setVelocity(p.getVelocity());
                    player.setxPos(p.getxPos());
                    player.setyPos(p.getyPos());
                    player.setRelativeXPosition(p.getRelativeXPosition());
                    player.setRelativeYPosition(p.getRelativeYPosition());



                }
            }
        }
    }

	public void setCharVal(int characterValue) {
		charVal = characterValue;
	}

	public void setCarVal(int carValue) {

	}

	public CharacterPanel getCharacterPanel() {
		return characterPanel;
	}

	public CarPanel getCarPanel() {
		return carPanel;
	}

	public void setCarPanel(CarPanel carPanel) {
		this.carPanel = carPanel;
	}

}
