public class Wall extends MapComponent{
    private boolean driveable;
    private double dimensions;
    private int xPosition;
    private int yPosition;

    Wall() {
        driveable = false;
        dimensions = 150.0;
    }

    @Override
    boolean getDriveable() {
        return driveable;
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
}
