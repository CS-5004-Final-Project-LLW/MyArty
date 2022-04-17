package Object;

/**
 * A class for bullet
 */
public class Bullet extends GameObject {


    public Bullet(CoordinateInt coordinate, CoordinateInt size, CoordinateInt screenSize) {
        super(coordinate, size, screenSize);
    }

    public Bullet(CoordinateInt coordinate, CoordinateInt screenSize) {
        super(coordinate, new CoordinateInt(1, 1), screenSize);
    }

    @Override
    public void createBoundary() {
        createDefaultBoundary();
    }
}
