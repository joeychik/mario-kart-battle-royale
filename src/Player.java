public class Player {
    private double xPos;
    private double yPos;
    private int dimensions;
    private double velocity;
    private double accel;
    private boolean brake;
    private double orientation;
    private int posRelativePosition = 0;
    private int negRelativePosition = 0;
    private boolean movingBackwards = false;

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

        if (velocity > 4) {
            velocity = 1;
        } else if (velocity < -0.6) {
            velocity = -0.6;
        }

        if (orientation >= (5 * Math.PI)/2) {
            orientation = (5 * Math.PI/2 - orientation) + Math.PI/2;
        } else if (orientation <= -(3*Math.PI)/2) {
            orientation = (Math.abs(orientation) - 3*Math.PI/2) + Math.PI/2;
        }


        if (((orientation < 0) && (orientation > -Math.PI)) || (((orientation > Math.PI)) && (orientation < (Math.PI * 2)))) {
            movingBackwards = true;
        } else {
            movingBackwards = false;
        }


        // accelerates/decelerates the player to 0 if brake is true
        if (brake) {
            accel = 0;
            if (velocity > 0) {
                velocity -= 0.005;
                if (velocity < 0) velocity = 0;
            } else if (velocity < 0) {
                velocity += 0.005;
                if (velocity > 0) velocity = 0;
            }
        }

        xPos += velocity * Math.cos(orientation);
        yPos += velocity * Math.sin(orientation);

        posRelativePosition = ((int) yPos) % 150;
        negRelativePosition = 150 - ((int) yPos % 150);
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

    public int getPosRelativePosition() {
        return posRelativePosition;
    }

    public void setPosRelativePosition(int relativePosition) {
        this.posRelativePosition = relativePosition;
    }

    public int getNegRelativePosition() {
        return negRelativePosition;
    }

    public void setNegRelativePosition(int relativePosition) {
        this.negRelativePosition = relativePosition;
    }

    public boolean isMovingBackwards() {
        return movingBackwards;
    }
}
