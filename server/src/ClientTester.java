import java.net.Socket;

class ClientTester {
    Socket mySocket = null;

    public static void main (String[] args) {
        Socket mySocket = null;
        try {
            mySocket = new Socket("127.0.0.1", 5000);
        } catch (Exception e) {
            System.out.println("connection error");
        }

    }
}
