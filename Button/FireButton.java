package Button;

import Coordinate.CoordinateInt;

public class FireButton extends Button {

    public FireButton(CoordinateInt coordinate, int width, int height) {
        super(coordinate, width, height);
    }

    @Override
    public boolean update() {
        return true;
    }
}
