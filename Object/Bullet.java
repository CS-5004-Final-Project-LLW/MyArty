package Object;

/**
 * A class for bullet
 */
public class Bullet extends GameObject {


    public Bullet(CoordinateInt coordinate, CoordinateInt size, CoordinateInt screenSize) {
        super(coordinate, size, screenSize);
    }

    public Bullet(CoordinateInt coordinate, CoordinateInt size) {
        super(coordinate, size, null);
    }

    public Bullet(CoordinateInt coordinate) {
        super(coordinate, new CoordinateInt(1, 1), null);
    }

    @Override
    public void createBoundary() {
        // setBoundary_max(null);
        // setBoundary_min(null);
        createDefaultBoundary();
    }

}
