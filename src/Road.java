import java.awt.*;

public class Road extends MapComponent{
    private boolean driveable;
    private double dimensions;
    private int xPosition;
    private int yPosition;
    private Rectangle hitBox;

    Road(int dimensions, int xPosition, int yPosition, boolean driveable) {
        super(dimensions,xPosition, yPosition, driveable);
        this.dimensions = dimensions;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.driveable = driveable;
        hitBox = new Rectangle(xPosition, yPosition, dimensions, dimensions);
    }

    Road() {

    }

    @Override
    public boolean getDriveable() {
        return driveable;
    }

    @Override
    public double getDimensions() {
        return dimensions;
    }

    @Override
    public void setDimensions(double dimensions) {
        this.dimensions = dimensions;

    }

    @Override
    public int getyPosition() {
        return yPosition;
    }

    @Override
    public void setyPosition(int position) {
        this.yPosition = position;
    }

    @Override
    public int getxPosition() {
        return xPosition;
    }

    @Override
    public void setxPosition(int position) {
        this.xPosition = position;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    @Override
    public void setDriveable(boolean driveable) {
        this.driveable = driveable;
    }
}
