import java.awt.*;

public class Marker extends MapComponent {
    private int markerNum;
    private boolean driveable;
    private double dimensions;
    private int xPosition;
    private int yPosition;
    private Rectangle hitBox;

    Marker(int dimensions, int xPosition, int yPosition, boolean driveable, int markerNum) {
        super(dimensions,xPosition, yPosition, driveable);
        this.dimensions = dimensions;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.driveable = driveable;
        hitBox = new Rectangle(xPosition, yPosition, dimensions, dimensions);
    }

    Marker() {
        super();
    }

    public int getMarkerNum() {
        return markerNum;
    }

    public void setMarkerNum(int markerNum) {
        this.markerNum = markerNum;
    }

    public boolean getDriveable() {
        return driveable;
    }

    @Override
    public void setDriveable(boolean driveable) {
        this.driveable = driveable;
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
    public int getxPosition() {
        return xPosition;
    }

    @Override
    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    @Override
    public int getyPosition() {
        return yPosition;
    }

    @Override
    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    @Override
    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }
}
