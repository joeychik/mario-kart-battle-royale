abstract public class MapComponent {
    abstract boolean getDriveable();
    private double dimensions;
    private int xPosition;
    private int yPosition;

    abstract public double getDimensions();

    abstract public void setDimensions(double dimensions);

    abstract public int getyPosition();
    abstract public void setyPosition(int position);

    abstract public int getxPosition();


    abstract public void setxPosition(int position);
}
