import java.awt.*;

public class Wall extends MapComponent{
    private boolean driveable;
    private double dimensions;
    private int xPosition;
    private int yPosition;
    private Rectangle hitBox;

    Wall(int dimensions, int xPosition, int yPosition, boolean driveable) {
        super(dimensions,xPosition, yPosition, driveable);
        this.dimensions = dimensions;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.driveable = driveable;
        hitBox = new Rectangle(xPosition, yPosition, dimensions, dimensions);
    }

    Wall () {

    }

    @Override
    public boolean getDriveable() {
        return driveable;
    }

    @Override
    public void setDriveable(boolean driveable) {
        this.driveable = driveable;
    }

    public double getDimensions() {
        return dimensions;
    }

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
}
