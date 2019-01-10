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

    abstract public double getDimensions();

    abstract public void setDimensions(double dimensions);

    abstract public int getyPosition();
    abstract public void setyPosition(int position);

    abstract public int getxPosition();


    abstract public void setxPosition(int position);

    abstract public Rectangle getHitBox();

    abstract public void setHitBox(Rectangle hitBox);

    abstract public boolean getDriveable();

    abstract public void setDriveable(boolean driveable);
}
