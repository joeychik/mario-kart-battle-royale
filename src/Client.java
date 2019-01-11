import java.net.Socket;

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
            try {

            }
        }
    }
}
