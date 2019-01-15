import java.util.ArrayList;

public class StartServerPacket {
    private String map;
    private ArrayList<String> playerNames = new ArrayList<>();
    private ArrayList<String> playerCharacterSprites = new ArrayList<>();
    private ArrayList<String> playerCarSprites = new ArrayList<>();

    StartServerPacket (String map, String playerName, String playerSprite, String carSprite) {
        this.map = map;
        playerNames.add(playerName);
        playerCharacterSprites.add(playerSprite);
        playerCarSprites.add(carSprite);
    }

    StartServerPacket () {
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public void addPlayerName(String playerNames) {
        this.playerNames.add(playerNames);
    }

    public ArrayList<String> getPlayerCharacterSprites() {
        return playerCharacterSprites;
    }

    public void addPlayerCharacterSprites(String playerCharacterSprite) {
        this.playerCharacterSprites.add(playerCharacterSprite);
    }

    public ArrayList<String> getPlayerCarSprites() {
        return playerCarSprites;
    }

    public void addPlayerCarSprites(String playerCarSprite) {
        this.playerCarSprites.add(playerCarSprite);
    }
}
