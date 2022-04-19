package Object;

import Coordinate.CoordinateInt;

/**
 * A class for bullet
 */
public class BulletShadow extends GameObject {


    public BulletShadow(CoordinateInt coordinate, CoordinateInt size, CoordinateInt screenSize) {
        super(coordinate, size, screenSize);
    }

    public BulletShadow(CoordinateInt coordinate, CoordinateInt size) {
        super(coordinate, size, null);
    }

    public BulletShadow(CoordinateInt coordinate) {
        super(coordinate, new CoordinateInt(1, 1), null);
    }

    @Override
    public void createBoundary() {
        createDefaultBoundary();
    }

}
