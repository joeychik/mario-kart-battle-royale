import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

class ServerRun {
    public static void main (String[] args) {
        Server server = new Server("res/mapone.txt");
        server.run();
    }
}

public class Server implements Runnable{
    private ServerSocket serverSock;// server socket for connection
    private ArrayList<Client> clients;
    private ServerGame serverGame;
    private String mapName;
    private ArrayList<Player> players;
    private Boolean accepting = true;

    public static void main(String[] args) {
        new Server("MapOne.txt");
        System.out.println("running");
    }

    Server(String mapName) {
        this.mapName = mapName;
        clients = new ArrayList<>();
        players = new ArrayList<>();
        serverGame = new ServerGame();
        run();
    }

    public void startGame() {
        accepting = false;
        serverGame.start();
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
        int lastPosition = 150;
        int mapIncrement = 0;
        int yMapPosition = 0;
        int xMapPosition = 0;
        private final int FRAMERATE = 60;
        private ArrayList<MapComponent> masterMarkerList = new ArrayList<>();

        ServerGame() {
            mapInfo = new MapReader(mapName);
            map = mapInfo.getMap();
            masterMarkerList = mapInfo.getMarkerList();
            gameLoopTimer = new Timer();
        }

        public void start() {
            gameLoopTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    for (int i = 0; i < players.size() - 1; i++) {
                        // may want to make more efficient but it's probably not a big deal because we won't have a lot of players
                        if (players.get(i).getHitBox().intersects(players.get(i+1).getHitBox())) {
                            //  collision(players.get(i), players.get(i+1));
                            players.get(i).setOrientation((Math.PI * 0.5) * (players.get(i).getOrientation() / Math.abs(players.get(i).getOrientation())));
                            players.get(i).setBrake(true);
                            players.get(i+1).setOrientation((Math.PI * 0.5) * (players.get(i+1).getOrientation() / Math.abs(players.get(i + 1).getOrientation())));
                            players.get(i+1).setBrake(true);
                        }
                    }

                    for (Player player : players) {
                        player.update();
                    }

                    for (Player p : players) {
                        yMapPosition = ((int)p.getyPos() /150) - 1;
                        xMapPosition = ((int)p.getxPos()/150) - 1;
                        if (map[yMapPosition][xMapPosition] instanceof Marker) {
                                for (MapComponent check: p.getMarkerList()) {
                                    if (p.getHitBox().intersects(check.getHitBox())) {
                                        p.setMarkersPassed(p.getMarkersPassed() + 1);
                                        p.getMarkerList().remove(check);
                                    }
                            }
                        } else if (map[yMapPosition][xMapPosition] instanceof FinishMarker) {
                            for (MapComponent marker: masterMarkerList) {
                                p.getMarkerList().add(marker);
                            }
                        }
                    }

                    Collections.sort(players);

                    ServerPacket packet = new ServerPacket(players);

                    for (Client client : clients) {
                    //    client.send(new ServerPacket(players));
                    }
                }
            }, 0, 1000 / FRAMERATE);
        }

        public MapComponent[][] getMap() {
            return map;
        }

        public void collision(Player playerOne, Player playerTwo) {

            //sets player orientation to face forward
//            playerOne.setOrientation((Math.PI * 0.5) * (playerOne.getOrientation() / Math.abs(playerOne.getOrientation())));
//            playerTwo.setOrientation((Math.PI * 0.5) * (playerTwo.getOrientation() / Math.abs(playerTwo.getOrientation())));

        }

        // why does this exist
//        public void startGame(ArrayList<Player> players) {
//            // TODO draft protocol
//        }
    }
}
