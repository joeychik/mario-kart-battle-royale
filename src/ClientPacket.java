public class ClientPacket extends Packet{
    private double velocity;
    private double accel;
    private double orientation;
    private boolean brake;

    public ClientPacket(double velocity, double accel, double orientation, boolean brake) {
        this.velocity = velocity;
        this.accel = accel;
        this.orientation = orientation;
        this.brake = brake;
    }

    public double getAccel() {
        return accel;
    }

    public boolean isBrake() {
        return brake;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getOrientation() {
        return orientation;
    }
}
