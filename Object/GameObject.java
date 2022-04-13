package Object;

/**
 * An abstarct class for GameObject like Cannon, Target, Bullet and so on
 */
public abstract class GameObject {
    private CoordinateInt coordinate;
    private CoordinateInt size;

    public CoordinateInt getCoordinate() {
        return new CoordinateInt(coordinate);
    }

    public void setCoordinate(CoordinateInt coordinate) {
        this.coordinate = new CoordinateInt(coordinate);
    }

    public CoordinateInt getSize() {
        return new CoordinateInt(size);
    }

    public void setSize(CoordinateInt size) {
        this.size = new CoordinateInt(size);
    }

    public GameObject(CoordinateInt coordinate, CoordinateInt size) {
        setCoordinate(coordinate);
        setSize(size);
    }

    public int getX() {
        return coordinate.x;
    }

    public int getY() {
        return coordinate.y;
    }



}
