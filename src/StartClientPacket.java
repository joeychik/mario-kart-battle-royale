/**
 * [StartClientPacket.java]
 * packet sent by client to server to set up client on server
 */

public class StartClientPacket extends Packet{
    private Player player;

    StartClientPacket(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
