public class Road extends MapComponent{
    private boolean driveable;
    private double dimensions;
    private int xPosition;
    private int yPosition;

    Road() {
        driveable = true;
        dimensions = 150.0;
    }

    @Override
    boolean getDriveable() {
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
}
