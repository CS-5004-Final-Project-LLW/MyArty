package Object;

public class CoordinateDouble extends Coordinate<Double> {

    public CoordinateDouble(Double x, Double y) {
        super(x, y);
    }

    public CoordinateDouble(Coordinate<Double> other) {
        super(other);
    }

    public CoordinateDouble(CoordinateInt other) {
        super((double) (other.x), (double) (other.y));
    }

    @Override
    public String toString() {
        return "CoordinateDouble [x=%.2f, y=%.2f]".formatted(x, y);
    }

}
