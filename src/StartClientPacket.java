public class StartClientPacket extends Packet{
    private String name;
    private String characterSprite;
    private String carSprite;

    StartClientPacket(String name, String characterSprite, String carSprite) {
        this.name = name;
        this.characterSprite = characterSprite;
        this.carSprite = carSprite;
    }

    public String getName() {
        return name;
    }

    public String getCharacterSprite() {
        return characterSprite;
    }

    public String getCarSprite() {
        return carSprite;
    }
}
