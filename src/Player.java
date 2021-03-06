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

    /**
     * Constructor
     * @param name
     * @param characterSprite
     * @param carSprite
     */
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

    /**
     * update
     * Updates player position based on velocity, acceleration, and orientation values
     */
    public void update() {

        //increase velocity
        velocity += accel;

        //capping velocity
        if (velocity > TERMINAL_VELOCITY) {
            velocity = TERMINAL_VELOCITY;
        } else if (velocity < -0.6) {
            velocity = -0.6;
        }

        //resets player orientation to avoid integer overflow and unexpected values
        if (orientation >= (5 * Math.PI)/2) {
            orientation = (5 * Math.PI/2 - orientation) + Math.PI/2;
        } else if (orientation <= -(3*Math.PI)/2) {
            orientation = (Math.abs(orientation) - 3*Math.PI/2) + Math.PI/2;
        }


        //check direction of movement
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

        //update player position
        xPos += velocity * Math.cos(orientation);
        yPos += velocity * Math.sin(orientation);

        //update player relative position for drawing
        relativeYPosition = ((int) yPos) % 150;
        relativeXPosition = ((int) xPos) % 150;

        //update hitbox
        hitBox.setLocation((int) xPos, (int) yPos);
    }

    /**
     * isReady
     * @return boolean ready
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * setReady
     * @param ready
     */
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    /**
     * getxPos
     * @return xPos
     */
    public double getxPos() {
        return xPos;
    }
    /**
     * getAccel
     * @return acceleration
     */
    public double getAccel() {
		return accel;
	}
    
    /**
     * isBrake
     * @return brake
     */
	public boolean isBrake() {
		return brake;
	}

    /**
     * setxPos
     * @param xPos
     */
    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    /**
     * getyPos
     * @return double
     */
    public double getyPos() {
        return yPos;
    }

    /**
     * set yPos
     * @param yPos
     */
    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    /**
     * getDimensions
     * @return int
     */
    public int getDimensions() {
        return dimensions;
    }

    /**
     * setDimensions
     * @param dimensions
     */
    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }

    /**
     * getVelocity
     * @return double
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * setVelocity
     * @param velocity
     */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /**
     * setAccel
     * @param accel
     */
    public void setAccel(double accel) {
        this.accel = accel;
    }

    /**
     * setBrake
     * @param brake
     */
    public void setBrake(boolean brake) {
        this.brake = brake;
    }

    /**
     * getOrientation
     * @return double
     */
    public double getOrientation() {
        return orientation;
    }

    /**
     * setOrientation
     * @param orientation
     */
    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    /**
     * getRelativePosition
     * @return int
     */
    public int getRelativeYPosition() {
        return relativeYPosition;
    }

    /**
     * setRelativeYPosition
     * @param relativeYPosition
     */
    public void setRelativeYPosition(int relativeYPosition) {
        this.relativeYPosition = relativeYPosition;
    }

    /**
     * getRelativeXPosition
     * @return int
     */
    public int getRelativeXPosition() {
        return relativeXPosition;
    }

    /**
     * setRelativeXPosition
     * @param relativeXPosition
     */
    public void setRelativeXPosition(int relativeXPosition) {
        this.relativeXPosition = relativeXPosition;
    }

    /**
     * isMovingBackwards
     * @return boolean
     */
    public boolean isMovingBackwards() {
        return movingBackwards;
    }

    /**
     * getCharacterSprite
     * @return int
     */
    public int getCharacterSprite() {
        return characterSprite;
    }

    /**
     * setCharacterSprite
     * @param characterSprite
     */
    public void setCharacterSprite(int characterSprite) {
        this.characterSprite = characterSprite;
    }

    /**
     * getCarSprite
     * @return int
     */
    public int getCarSprite() {
        return carSprite;
    }

    /**
     * setCarSprite
     * @param carSprite
     */
    public void setCarSprite(int carSprite) {
        this.carSprite = carSprite;
    }

    /**
     * getName
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * setName
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getHitBox
     * @return Rectangle
     */
    public Rectangle getHitBox() {
        return hitBox;
    }

    /**
     * setHitBox
     * @param hitBox
     */
    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    /**
     * getMarkerList
     * @return ArrayList(MapComponent)
     */
    public ArrayList<MapComponent> getMarkerList() {
        return markerList;
    }

    /**
     * getMarkersPassed
     * @return int
     */
    public int getMarkersPassed() {
        return markersPassed;
    }

    /**
     * setMarkersPassed
     * @param markersPassed
     */
    public void setMarkersPassed(int markersPassed) {
        this.markersPassed = markersPassed;
    }

    /**
     * getLapsCompleted
     * @return int
     */
    public int getLapsCompleted() {
        return lapsCompleted;
    }

    /**
     * setLapsCompleted
     * @param lapsCompleted
     */
    public void setLapsCompleted(int lapsCompleted) {
        this.lapsCompleted = lapsCompleted;
    }

    /**
     * getLastMarker
     * @return MapComponent
     */
    public MapComponent getLastMarker() {
        return lastMarker;
    }

    /**
     * setLastMarker
     * @param lastMarker
     */
    public void setLastMarker(MapComponent lastMarker) {
        this.lastMarker = lastMarker;
    }

    /**
     * isFinishedrace
     * @return boolean
     */
    public boolean isFinishedRace() {
        return finishedRace;
    }

    /**
     * setFinishedRace
     * @param finishedRace
     */
    public void setFinishedRace(boolean finishedRace) {
        this.finishedRace = finishedRace;
    }

    /**
     * getPlayerID
     * @return
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * setPlayerID
     * @param playerID
     */
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
