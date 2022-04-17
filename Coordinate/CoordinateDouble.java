package Coordinate;

/**
 * A class for coordinate storing with Double type
 */
public class CoordinateDouble extends Coordinate<Double> {

    public CoordinateDouble(Double x, Double y) {
        super(x, y);
    }

    public CoordinateDouble(CoordinateDouble other) {
        super(other);
    }

    /**
     * Convert coordinate with Integer to the one with Double
     * 
     * @param other coordinate with Integer
     */
    public CoordinateDouble(CoordinateInt other) {
        super((double) (other.x), (double) (other.y));
    }

    @Override
    public String toString() {
        return "CoordinateDouble [x=%.2f, y=%.2f]".formatted(x, y);
    }

}
