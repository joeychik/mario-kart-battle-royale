import java.util.ArrayList;

public class ServerPacket extends Packet{
    ArrayList<Player> playerList;

    public ServerPacket(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
}
