package Object;

public class CoordinateInt extends Coordinate<Integer> {

    public CoordinateInt(Integer x, Integer y) {
        super(x, y);
    }

    public CoordinateInt(Coordinate<Integer> other) {
        super(other);
    }

    public CoordinateInt(CoordinateDouble other) {
        super(other.x.intValue(), other.y.intValue());
    }
}
