package Object;

import Coordinate.CoordinateInt;

/**
 * A class for Explosion
 */
public class Explosion extends GameObject {

    public Explosion(CoordinateInt coordinate, CoordinateInt size, CoordinateInt screenSize) {
        super(coordinate, size, screenSize);
    }

    public Explosion(CoordinateInt coordinate, CoordinateInt screenSize) {
        super(coordinate, new CoordinateInt(1, 1), screenSize);
    }

    @Override
    protected void createBoundary() {
        setBoundary_min(new CoordinateInt(1, 1));
        setBoundary_max(new CoordinateInt(getScreenSize().x, getScreenSize().y));
    }
}
