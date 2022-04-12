package Object;

public class Bullet extends GameObject {

    public Bullet(CoordinateInt coordinate, CoordinateInt size) {
        super(coordinate, size);
    }

    public Bullet(CoordinateInt coordinate) {
        super(coordinate, new CoordinateInt(1, 1));
    }
}
