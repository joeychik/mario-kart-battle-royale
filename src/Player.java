import java.awt.*;
import java.util.ArrayList;

public class Player implements Comparable<Player> {
    private double xPos;
    private double yPos;
    private int dimensions;
    private double velocity;
    private double accel;
    private boolean brake;
    private double orientation;
    private int relativeYPosition = 0;
    private int relativeXPosition = 0;
    private transient boolean ready = false;
    private int playerID;
    private boolean movingBackwards = false;
    private String name;
    private int characterSprite;
    private int carSprite;
    private Rectangle hitBox;
    private transient MapComponent[][] map;
    private transient ArrayList<MapComponent> markerList = new ArrayList<>();
    private MapComponent lastMarker;
    private int markersPassed;
    private int lapsCompleted;
    private boolean finishedRace;

    private static int TERMINAL_VELOCITY = 3;

    Player(String name, int characterSprite, int carSprite) {
        xPos = 250.0;
        yPos = 750.0;
        dimensions = 25;
        velocity = 0;
        accel = 0;
        markersPassed = 0;
        lapsCompleted = 0;
        brake = false;
        finishedRace = false;
        lastMarker = null;
        hitBox = new Rectangle((int)xPos, (int)yPos, dimensions, dimensions);
        orientation = 0.5 * Math.PI;
        this.characterSprite = characterSprite;
        this.carSprite = carSprite;
        this.name = name;
    }

    public void update() {
        velocity += accel;

        if (velocity > TERMINAL_VELOCITY) {
            velocity = TERMINAL_VELOCITY;
        } else if (velocity < -0.6) {
            velocity = -0.6;
        }

        if (orientation >= (5 * Math.PI)/2) {
            orientation = (5 * Math.PI/2 - orientation) + Math.PI/2;
        } else if (orientation <= -(3*Math.PI)/2) {
            orientation = (Math.abs(orientation) - 3*Math.PI/2) + Math.PI/2;
        }


        if ((
                (orientation < 0) && (orientation > -Math.PI)
        ) || (
                (orientation > Math.PI) && (orientation < Math.PI * 2)
        )) {
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

        relativeYPosition = ((int) yPos) % 150;
        relativeXPosition = ((int) xPos) % 150;

        hitBox.setLocation((int) xPos, (int) yPos);
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
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

    public int getRelativeYPosition() {
        return relativeYPosition;
    }

    public void setRelativeYPosition(int relativeYPosition) {
        this.relativeYPosition = relativeYPosition;
    }

    public int getRelativeXPosition() {
        return relativeXPosition;
    }

    public void setRelativeXPosition(int relativeXPosition) {
        this.relativeXPosition = relativeXPosition;
    }

    public boolean isMovingBackwards() {
        return movingBackwards;
    }

    public int getCharacterSprite() {
        return characterSprite;
    }

    public void setCharacterSprite(int characterSprite) {
        this.characterSprite = characterSprite;
    }

    public int getCarSprite() {
        return carSprite;
    }

    public void setCarSprite(int carSprite) {
        this.carSprite = carSprite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public ArrayList<MapComponent> getMarkerList() {
        return markerList;
    }

    public int getMarkersPassed() {
        return markersPassed;
    }

    public void setMarkersPassed(int markersPassed) {
        this.markersPassed = markersPassed;
    }

    public int getLapsCompleted() {
        return lapsCompleted;
    }

    public void setLapsCompleted(int lapsCompleted) {
        this.lapsCompleted = lapsCompleted;
    }

    public MapComponent getLastMarker() {
        return lastMarker;
    }

    public void setLastMarker(MapComponent lastMarker) {
        this.lastMarker = lastMarker;
    }

    public double getAccel() {
        return accel;
    }

    public boolean isBrake() {
        return brake;
    }

    public boolean isFinishedRace() {
        return finishedRace;
    }

    public void setFinishedRace(boolean finishedRace) {
        this.finishedRace = finishedRace;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    @Override
    public int compareTo(Player player) {
//    	try {
//            if (this.getMarkerList().size() < player.getMarkerList().size()) {
//                return -1;
//            } else if (this.getMarkerList().size() > player.getMarkerList().size()) {
//                return 1;
//            } else {
//                return 0;
//            }
//    	} catch (Exception e) {
//    		return 0;
//    	}
    	
    	try {
            if (markersPassed < player.getMarkersPassed()) {
                return -1;
            } else if (markersPassed > player.getMarkersPassed()) {
                return 1;
            } else {
                return 0;
            }
    	} catch (Exception e) {
    		return 0;
    	}
    	

    }
}
