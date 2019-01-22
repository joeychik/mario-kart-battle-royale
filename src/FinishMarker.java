import java.awt.Rectangle;

public class FinishMarker extends MapComponent {

    private boolean driveable;
    private double dimensions;
    private int xPosition;
    private int yPosition;
    private Rectangle hitBox;

    /**
     * Constructor
     * @param dimensions
     * @param xPosition
     * @param yPosition
     * @param driveable
     */
    FinishMarker(int dimensions, int xPosition, int yPosition, boolean driveable) {
        super(dimensions, xPosition, yPosition, driveable);
        this.dimensions = dimensions;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.driveable = driveable;
        hitBox = new Rectangle(xPosition, yPosition, dimensions, dimensions);
    }

    FinishMarker() {
        super();
    }

    /**
     * getDimensions
     * @return dimension
     */
    @Override
    public double getDimensions() {
        return dimensions;
    }

    /**
     * setDimensions
     * @param dimensions
     */
    @Override
    public void setDimensions(double dimensions) {

    }

    /**
     * getyPosition
     * @return yPosition
     */
    @Override
    public int getyPosition() {
        return yPosition;
    }

    /**
     * setyPosition
     * @param position
     */
    @Override
    public void setyPosition(int position) {
        this.yPosition = position;
    }

    /**
     * getxPosition
     * @return xPosition
     */
    @Override
    public int getxPosition() {
        return xPosition;
    }

    /**
     * setxPosition
     * @param position
     */
    @Override
    public void setxPosition(int position) {
        this.xPosition = position;
    }

    /**
     * getHitBox
     * @return Rectangle
     */
    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    /**
     * setHitBox
     * @param hitBox
     */
    @Override
    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    /**
     * getDriveable
     * @return boolean
     */
    @Override
    public boolean getDriveable() {
        return driveable;
    }

    /**
     * setDriveable
     * @param driveable
     */
    @Override
    public void setDriveable(boolean driveable) {
        this.driveable = driveable;
    }

}
