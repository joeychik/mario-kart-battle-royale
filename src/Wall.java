public class Wall extends MapComponent{
    private boolean driveable;
    private double dimensions;

    Wall() {
        driveable = false;
        dimensions = 50.0;
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

}
