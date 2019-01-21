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
import java.sql.Wrapper;

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

    public StartClientPacket getStartClientPacket() {
        return startClientPacket;
    }

    public Player getPlayer() {
        return player;
    }

    public void startThread() {
        Thread t = new Thread(connectionHandler);
        running = true;
        t.start();
    }

    public void send(StartServerPacket packet) {
        System.out.println(packet);
        connectionHandler.send(packet);
    }

    private class ConnectionHandler implements Runnable{
        private Socket socket;
        private Gson gson = new Gson();
        private JsonReader input;
        private JsonWriter output;

        private ConnectionHandler(Socket socket) throws JsonSyntaxException, IOException{
            this.socket = socket;
            this.socket.setSoTimeout(100200);
            startClientPacket = new StartClientPacket("name", "place", "holder");
            // initialize json readers/writers and attach them to the socket's input/output streams
            input = new JsonReader(new InputStreamReader(socket.getInputStream()));
            output = new JsonWriter(new OutputStreamWriter(socket.getOutputStream()));

            // set up client
            WrapperPacket packet = gson.fromJson(input, WrapperPacket.class);
            System.out.println("receive");
            System.out.println(packet.getData());
            startClientPacket = (StartClientPacket) packet.getData();

            MapComponent[][] map = server.getServerGame().getMap();
            player = new Player(
                    startClientPacket.getName(),
                    startClientPacket.getCharacterSprite(),
                    startClientPacket.getCarSprite(),
                    map
            );
        }

        public void run() {

            while(running) {  // loop unit a message is received
                try {
                    // adds message to messageBuffer
                    WrapperPacket wrapperPacket = gson.fromJson(input, WrapperPacket.class);
                    ClientPacket packet = (ClientPacket) wrapperPacket.getData();
                    System.out.println("got message");

                } catch (JsonIOException e) {
                    System.err.println("Failed to receive msg from the socket");
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
        }

        public void send(Packet packet) {
            System.out.println(packet.getClass());
            gson.toJson(new WrapperPacket<>(packet), WrapperPacket.class, output);
            try {
                output.flush();
            } catch (IOException e) {
                System.out.println("failed to send packet");
            }
        }
    }
}
