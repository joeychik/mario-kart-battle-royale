import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
    private ServerSocket serverSock;// server socket for connection
    private ArrayList<Client> clients;
    private ServerGame serverGame;
    private ArrayList<Player> players;
    private Boolean accepting = true;

    Server() {
    }

    public void startGame(String mapName) {
        accepting = false;
        serverGame = new ServerGame(mapName);
    }

    public void run() {
        System.out.println("Waiting for a client connection..");

        Socket s = null;//hold the client connection

        try {
            serverSock = new ServerSocket(5000);  //assigns an port to the server
            //serverSock.setSoTimeout(25000);  //25 second timeout
            while(accepting) {  //this loops to accept multiple clients
                s = serverSock.accept();  //wait for connection
                System.out.println("Client connected");
                Client c = new Client(s);
                clients.add(c);
                players.add(c.getPlayer());
                StartClientPacket startClientPacket = new StartClientPacket(c.getPlayer().getName(),
                        c.getPlayer().getCharacterSprite(), c.getPlayer().getCarSprite());

                for (Client client : clients) {
                    client.send(startClientPacket);
                }

                c.startThread();
            }
        } catch (Exception e) {
            System.out.println("Error accepting connection");
            e.printStackTrace();
            //close all and quit
            try {
                s.close();
            }catch (Exception e1) {
                System.out.println("Failed to close socket");
            }
            System.exit(-1);
        }
    }

    private class ServerGame {
        String mapName;
        MapReader mapInfo;
        MapComponent[][] map;
        int lastPosition = 150;
        int mapIncrement = 0;
        int yMapPosition = 0;


        ServerGame (String mapName) {
            this.mapName = mapName;
            mapInfo = new MapReader(mapName);
            map = mapInfo.getMap();
        }

        public void serverGameLoop() {
            
        }

        // why does this exist
//        public void startGame(ArrayList<Player> players) {
//            // TODO draft protocol
//        }
    }
}
