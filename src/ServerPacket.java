import java.util.ArrayList;

public class ServerPacket implements Packet{
    ArrayList<Player> playerList;

    public ServerPacket(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
}
