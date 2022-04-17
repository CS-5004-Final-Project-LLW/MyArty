package Object;

/**
 * A class for Target
 */
public class Target extends GameObject {

    public Target(CoordinateInt coordinate, CoordinateInt size, CoordinateInt screenSize) {
        super(coordinate, size,screenSize);
    }

    public Target(CoordinateInt coordinate, CoordinateInt screenSize) {
        super(coordinate, new CoordinateInt(3, 3),screenSize);
    }
}
