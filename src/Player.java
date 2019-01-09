public class Player {
    private double xPos;
    private double yPos;
    private int dimensions;
    private double velocity;
    private double accel;
    private boolean brake;
    private double orientation;
    private int relativePosition = 0;

    Player() {
        xPos = 250.0;
        yPos = 750.0;
        dimensions = 25;
        velocity = 0;
        accel = 0;
        brake = false;
        orientation = 0.5 * Math.PI;
    }

    public void update() {
        velocity += accel;

        if (velocity > 1) {
            velocity = 1;
        } else if (velocity < -0.6) {
            velocity = -0.6;
        }

        // accelerates/decelerates the player to 0 if brake is true
        if (brake) {
            accel = 0;
            if (velocity > 0) {
                System.out.println("hit");
                velocity -= 0.0025;
                if (velocity < 0) velocity = 0;
            } else if (velocity < 0) {
                velocity += 0.0025;
                if (velocity > 0) velocity = 0;
            }
            System.out.println(velocity);
        }

        xPos += velocity * Math.cos(orientation);
        yPos += velocity * Math.sin(orientation);

        relativePosition = ((int) yPos) % 150;
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public int getDimensions() {
        return dimensions;
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setAccel(double accel) {
        this.accel = accel;
    }

    public void setBrake(boolean brake) {
        this.brake = brake;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public int getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(int relativePosition) {
        this.relativePosition = relativePosition;
    }
}
