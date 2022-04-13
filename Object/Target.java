package Object;

/**
 * A class for Target
 */
public class Target extends GameObject {

    public Target(CoordinateInt coordinate, CoordinateInt size) {
        super(coordinate, size);
    }

    public Target(CoordinateInt coordinate) {
        super(coordinate, new CoordinateInt(3, 3));
    }
}
