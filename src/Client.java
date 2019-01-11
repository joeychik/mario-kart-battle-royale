import java.net.Socket;
import java.net.SocketException;

public class Client {
    private Socket socket;
    private ConnectionHandler connectionHandler;

    Client(Socket socket) {
        this.socket = socket;
    }

    public void startThread() {
        connectionHandler = new ConnectionHandler();
        Thread t = new Thread(connectionHandler);
        t.start();
    }

    private class ConnectionHandler implements Runnable{
        private ConnectionHandler() {

        }

        public void run() {
            while(running) {  // loop unit a message is received
                try {
                    // adds message to messageBuffer
                    Message msg = gson.fromJson(input, Message.class);
                    System.out.println("got message");
                    lastMessageTime = clock.millis();
                    messageBuffer.offer(msg);
                } catch (JsonIOException e) {
                    System.err.println("Failed to receive msg from the socket");
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    // check if socket is disconnected
                    if (e.getCause().getClass() == SocketException.class) {
                        System.out.println("closing socket");
                        close();
                        running = false;
                    } else {
                        System.err.println("problem with JSON syntax");
                        close();
                        running = false;
                        e.printStackTrace();
                    }
                }
            }
        }

        public void send() {

        }
    }
}
