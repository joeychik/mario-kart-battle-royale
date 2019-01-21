import java.util.ArrayList;

public class ServerPacket extends Packet{
    public static final int START_PACKET = 0;
    public static final int GAME_PACKET = 1;

    private int packetType;
    private String map = null;
    private ArrayList<Player> playerList;

    ServerPacket(String map, ArrayList<Player> playerList) {
        packetType = START_PACKET;
        this.map = map;
        this.playerList = playerList;
    }
    ServerPacket(ArrayList<Player> playerList) {
        packetType = GAME_PACKET;
        this.playerList = playerList;
    }

    public int getPacketType() {
        return packetType;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }


}
