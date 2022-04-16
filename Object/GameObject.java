package Object;

/**
 * An abstarct class for GameObject like Cannon, Target, Bullet and so on
 */
public abstract class GameObject {
    private CoordinateInt coordinate;
    private CoordinateInt size; // rectangle

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


    /**
     * @param screenSize
     * @return boolean {@code ture} if the coordinate is valid according to the screen size
     */
    public boolean isValid(CoordinateInt screenSize) {
        return coordinate.x >= 0 && coordinate.x < screenSize.x && coordinate.y >= 0
                && coordinate.y < screenSize.y;
    }


    /**
     * Change the coordiante to a valid one
     * 
     * @param screenSize
     */
    public void legalizeCoordinate(CoordinateInt screenSize) {
        if (coordinate.x < 0) {
            coordinate.x = 0;
        }
        if (coordinate.x > screenSize.x - 1) {
            coordinate.x = screenSize.x - 1;
        }
        if (coordinate.y < 0) {
            coordinate.y = 0;
        }
        if (coordinate.y > screenSize.y - 1) {
            coordinate.y = screenSize.y - 1;
        }
    }

}
