package Object;

public class Coordinate<T> {
    public T x;
    public T y;

    public Coordinate(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate<T> other) {
        x = other.x;
        y = other.y;
    }

    @Override
    public String toString() {
        return "CoordinatePair [x=" + x + ", y=" + y + "]";
    }
}
