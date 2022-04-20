package Coordinate;

/**
 * A class for coordinate storing with Integer type
 */
public class CoordinateInt extends Coordinate<Integer> {

    public CoordinateInt(Integer x, Integer y) {
        super(x, y);
    }

    public CoordinateInt(CoordinateInt other) {
        super(other);
    }

    /**
     * Convert coordinate with Double to the one with Integer
     * 
     * @param other coordinate with Double
     */
    public CoordinateInt(CoordinateDouble other) {
        super(other.x.intValue(), other.y.intValue());
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
