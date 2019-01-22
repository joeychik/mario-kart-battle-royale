public class ClientPacket extends Packet{
    private double velocity;
    private double accel;
    private double orientation;
    private boolean brake;


    /**
     * Constructor
     * @param velocity
     * @param accel
     * @param orientation
     * @param brake
     */
    public ClientPacket(double velocity, double accel, double orientation, boolean brake) {
        this.velocity = velocity;
        this.accel = accel;
        this.orientation = orientation;
        this.brake = brake;
    }

    /**
     * getAccel
     * @return acceleration
     */
    public double getAccel() {
        return accel;
    }

    /**
     *
     * @return brake (true/false)
     */
    public boolean isBrake() {
        return brake;
    }

    /**
     * getVelocity
     * @return velocity
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * getOrientation
     * @return orientation
     */
    public double getOrientation() {
        return orientation;
    }
}
