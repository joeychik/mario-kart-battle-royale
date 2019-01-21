public class StartClientPacket extends Packet{
    private Player player;

    StartClientPacket(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
