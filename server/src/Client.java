import com.google.gson.Gson;  
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * [Client.java]
 * Server-side representation of a connected client
 * all packets to and from a client are handled through this class
 */

public class Client {
    private Socket socket;
    private ConnectionHandler connectionHandler;
    private boolean running;
    private Player player;
    private StartClientPacket startClientPacket;
    private Server server;

    Client(Socket socket, Server server) throws JsonSyntaxException, IOException {
        this.server = server;
        connectionHandler = new ConnectionHandler(socket);
    }

    /**
     * getStartClientPacket()
     * returns the startClientPacket from this client
     * @return
     */
    public StartClientPacket getStartClientPacket() {
        return startClientPacket;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * startThread()
     * Starts a thread to handle receiving packets from this client
     */
    public void startThread() {
        Thread t = new Thread(connectionHandler);
        running = true;
        t.start();
    }

    /**
     * send()
     * sends a packet to this client
     * @param packet packet to be sent
     */
    public void send(ServerPacket packet) {
        //System.out.println(packet);
        connectionHandler.send(packet);
    }

    /**
     * ConnectionHandler.java
     * holds the input and output writers for Client, serializes and deserializes incoming/outbound packets into their corresponding java classes
     */
    private class ConnectionHandler implements Runnable{
        private Socket socket;
        private Gson gson = new Gson();
        private JsonReader input;
        private JsonWriter output;

        private ConnectionHandler(Socket socket) throws JsonSyntaxException, IOException{
            this.socket = socket;
            // initialize json readers/writers and attach them to the socket's input/output streams
            input = new JsonReader(new InputStreamReader(socket.getInputStream()));
            output = new JsonWriter(new OutputStreamWriter(socket.getOutputStream()));

            // set up client
            startClientPacket = gson.fromJson(input, StartClientPacket.class);

            //MapComponent[][] map = server.getServerGame().getMap();
            player = startClientPacket.getPlayer();
        }

        public void run() {
            try {
                // handle packets sent by client while in lobby (before the game starts)
                while (!server.getServerGame().isInGame()) { // checks to see if server is in a game (if not, then the server is still in lobby)
                    gson.fromJson(input, ClientPacket.class);
                    // player shows that they are ready by sending ClientPackets
                    player.setReady(true);
                    server.startGame();
                }
                // handles packets sent during the game
                while (running) {
                    ClientPacket packet = gson.fromJson(input, ClientPacket.class);
                    player.setOrientation(packet.getOrientation());
                    player.setAccel(packet.getAccel());
                    player.setBrake(packet.isBrake());
                    player.setVelocity(packet.getVelocity());
                }
            } catch (JsonIOException e) {
                System.err.println("Failed to receive packet from the socket");
                e.printStackTrace();
            } catch (JsonSyntaxException e) {
                // check if socket is disconnected
                try {
                    if (e.getCause() instanceof SocketException) {
                        System.out.println("closing socket");
                        server.removeClient(Client.this);
                        input.close();
                        output.close();
                        socket.close();
                        running = false;
                    } else {
                        System.err.println("problem with JSON syntax");
                        server.removeClient(Client.this);
                        input.close();
                        output.close();
                        socket.close();
                        running = false;
                        e.printStackTrace();
                    }
                } catch (IOException e1) {
                    System.err.println("couldnt close socket");
                    e.printStackTrace();
                }
            }
        }

        // sends packets to client
        // is synchronized to prevent overlapping Timer tasks from Server
        private synchronized void send(ServerPacket packet) {
            gson.toJson(packet, ServerPacket.class, output); // serializes the packet object as a JSON and sends it to the JsonWriter output
            try {
                output.flush();
            } catch (IOException e) {
                System.out.println("failed to send packet");
            }
        }
    }
}
