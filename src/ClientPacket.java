public class ClientPacket implements Packet{
    private double xPos;
    private double yPos;
    private double velocity;
    private double orientation;
    private boolean movingBackwards;

    ClientPacket (double xPos, double yPos, double velocity, double orientation, boolean movingBackwards) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.velocity = velocity;
        this.orientation = orientation;
        this.movingBackwards = movingBackwards;
    }


    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public double getVelocity() {
        return velocity;
    }


}
