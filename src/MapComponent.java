import java.awt.*;

abstract public class MapComponent {
    private boolean driveable;
    private double dimensions;
    private int xPosition;
    private int yPosition;
    private Rectangle hitBox;

    MapComponent(int dimensions, int xPosition, int yPosition, boolean driveable) {
        this.dimensions = dimensions;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.driveable = driveable;
    }

    MapComponent() {

    }

    /**
     * getDimensions
     * @return double
     */
    abstract public double getDimensions();

    /**
     * setDimensions
     * @param dimensions
     */
    abstract public void setDimensions(double dimensions);

    /**
     * getyPosition
     * @return int
     */
    abstract public int getyPosition();

    /**
     * setyPosition
     * @param position
     */
    abstract public void setyPosition(int position);

    /**
     * getxPosition
     * @return int
     */
    abstract public int getxPosition();

    /**
     * setxPosition
     * @param position
     */
    abstract public void setxPosition(int position);

    /**
     * getHitBox
     * @return Rectangle
     */
    abstract public Rectangle getHitBox();

    /**
     * setHitBox
     * @param hitBox
     */
    abstract public void setHitBox(Rectangle hitBox);

    /**
     * getDriveable
     * @return boolean
     */
    abstract public boolean getDriveable();

    /**
     * setDriveable
     * @param driveable
     */
    abstract public void setDriveable(boolean driveable);
}
