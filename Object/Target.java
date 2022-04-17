package Object;

import Coordinate.CoordinateInt;

/**
 * A class for Target
 */
public class Target extends GameObject {

    public Target(CoordinateInt coordinate, CoordinateInt size, CoordinateInt screenSize) {
        super(coordinate, size, screenSize);
    }

    public Target(CoordinateInt coordinate, CoordinateInt screenSize) {
        super(coordinate, new CoordinateInt(3, 3), screenSize);
    }

    @Override
    protected void createBoundary() {
        setBoundary_min(new CoordinateInt(getScreenSize().x / 2 + 1, 1));
        setBoundary_max(new CoordinateInt(getScreenSize().x - 1, getScreenSize().y / 2 - 1));
    }
}
