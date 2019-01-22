import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class Server implements Runnable{
    private ServerSocket serverSock;// server socket for connection
    private ArrayList<Client> clients;
    private ServerGame serverGame;
    private String mapName;
    private ArrayList<Player> players;
    private Boolean accepting = true;
    private int lapCount;
    private String password;
    private int maxPlayers;

    public static void main(String[] args) {
        new Server("MapOne.txt", 3, "", 3);
        System.out.println("running");
    }

    /**
     * Server constructor
     * @param mapName indicates textfile to read map from
     * @param lapCount max number of laps
     * @param password password for private server
     * @param maxPlayer max player num
     */
    Server(String mapName, int lapCount, String password, int maxPlayer) {
        this.mapName = mapName;
        this.lapCount = lapCount;
        this.password = password;
        this.maxPlayers = maxPlayer;
        clients = new ArrayList<>();
        players = new ArrayList<>();
        serverGame = new ServerGame();

        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * startGame
     * begins game, stops accepting new players
     */
    public void startGame() {
        System.out.println("attempt to start");
        boolean start = false;
        for (Player p : players) {
            start = p.isReady();
        }

        if (start) {
            System.out.println("start");
            accepting = false;
            serverGame.start();
        }
    }

    public ServerGame getServerGame() {
        return serverGame;
    }

    public void removeClient(Client client) {
        try {
            clients.remove(client);
            players.remove(client.getPlayer());
        } catch (NullPointerException e) {
            System.out.println("player not created yet");
        }
    }

    public void run() {
        System.out.println("Waiting for a client connection..");

        Socket s = null;

        try {
            serverSock = new ServerSocket(5000);  //assigns an port to the server
            while(accepting) {  //this loops to accept multiple clients
                s = serverSock.accept();  //wait for connection
                System.out.println("Client connected");

                Client c = null;
                try {
                    c = new Client(s, this);
                    clients.add(c);
                    players.add(c.getPlayer());
                    c.getPlayer().setPlayerID(players.size());
                    c.startThread();

                    for (Client client : clients) {
                        client.send(new ServerPacket(mapName, players));
                    }
                } catch (JsonSyntaxException e) {
                    System.err.println("client timed out");
                    e.printStackTrace();
                    removeClient(c);
                    s.close();
                } catch (IOException e) {
                    System.err.println("couldnt connect to client");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Error accepting connection");
            e.printStackTrace();
            //close all and quit
            try {
                s.close();
            } catch (Exception e1) {
                System.out.println("Failed to close socket");
            }
            System.exit(-1);
        }
    }

    public class ServerGame {
        MapReader mapInfo;
        ServerPacket packet;
        MapComponent[][] map;
        Timer gameLoopTimer;
        int yMapPosition = 0;
        int xMapPosition = 0;
        int playersFinished = 0;
        private final int FRAMERATE = 30;
        private ArrayList<MapComponent> masterMarkerList = new ArrayList<>();
        private boolean inGame = false;

        /**
         * constructor
         */
        ServerGame() {
            mapInfo = new MapReader(mapName);
            map = mapInfo.getMap();
            masterMarkerList = mapInfo.getMarkerList();
            gameLoopTimer = new Timer();
        }

        public boolean isInGame() {
            return inGame;
        }

        /**
         * start
         * infinite loop of game calculations
         */
        public void start() {
            inGame = true;

            gameLoopTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    //System.out.println("timer run");

                    if (playersFinished == players.size()) {
                        //finished game code
                        gameLoopTimer.cancel();
                    }

                    //player - player hit detection
                    for (int i = 0; i < players.size() - 1; i++) {
                        // may want to make more efficient but it's probably not a big deal because we won't have a lot of players
                        if (players.get(i).getHitBox().intersects(players.get(i+1).getHitBox())) {

                            //inverts player orientation
                            players.get(i).setOrientation((Math.PI * 0.5) * (players.get(i).getOrientation() / Math.abs(players.get(i).getOrientation())));
                            players.get(i).setBrake(true);
                            players.get(i+1).setOrientation((Math.PI * 0.5) * (players.get(i+1).getOrientation() / Math.abs(players.get(i + 1).getOrientation())));
                            players.get(i+1).setBrake(true);
                        }
                    }

                    //update player's positions
                    for (Player player : players) {
                        player.update();
                    }

                    //position and leaderboard calculations


                    //sorts players in array by number of markers passed
                    Collections.sort(players);

                    //creates a new packet to send
                    ServerPacket packet = new ServerPacket(players);

                    //sends packet
                    for (Client client : clients) {
                        client.send(packet);
                        //System.out.println("send serverpacket");
                    }
                }
            }, 0, 1000 / FRAMERATE); //delay and framerate
        }

        /**
         * getMap
         * @return 2d map array
         */
        public MapComponent[][] getMap() {
            return map;
        }

        // why does this exist
//        public void startGame(ArrayList<Player> players) {
//            // TODO draft protocol
//        }
    }
}
