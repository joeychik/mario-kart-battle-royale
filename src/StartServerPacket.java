import java.util.ArrayList;

public class StartServerPacket extends ServerPacket{
    private String map;
    private ArrayList<Player> playerList;

    StartServerPacket (String map, ArrayList<Player> playerList) {
        super(playerList);
        this.map = map;
        this.playerList = playerList;
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
