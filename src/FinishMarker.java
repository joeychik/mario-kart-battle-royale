import java.awt.*;

public class FinishMarker extends MapComponent {

    FinishMarker(int dimensions, int xPosition, int yPosition, boolean driveable) {
        super(dimensions, xPosition, yPosition, driveable);
    }

    FinishMarker() {
        super();
    }

    @Override
    public double getDimensions() {
        return 0;
    }

    @Override
    public void setDimensions(double dimensions) {

    }

    @Override
    public int getyPosition() {
        return 0;
    }

    @Override
    public void setyPosition(int position) {

    }

    @Override
    public int getxPosition() {
        return 0;
    }

    @Override
    public void setxPosition(int position) {

    }

    @Override
    public Rectangle getHitBox() {
        return null;
    }

    @Override
    public void setHitBox(Rectangle hitBox) {

    }

    @Override
    public boolean getDriveable() {
        return true;
    }

    @Override
    public void setDriveable(boolean driveable) {

    }

}
