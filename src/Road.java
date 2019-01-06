public class Road extends MapComponent{
    private boolean driveable;
    private double dimensions;

    Road() {
        driveable = true;
        dimensions = 50.0;
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
}
