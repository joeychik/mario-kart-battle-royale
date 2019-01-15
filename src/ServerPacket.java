public class ServerPacket {
    private String[] players;
    private double[] xPos;
    private double[] yPos;
    private double[] velocity;

    public ServerPacket(String[] players, double[] xPos, double[] yPos, double[] velocity) {
        this.players = players;
        this.xPos = xPos;
        this.yPos = yPos;
        this.velocity = velocity;
    }

    public String[] getPlayers() {
        return players;
    }

    public double[] getxPos() {
        return xPos;
    }

    public double[] getyPos() {
        return yPos;
    }

    public double[] getVelocity() {
        return velocity;
    }
}
