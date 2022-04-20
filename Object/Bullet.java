package Object;

import Coordinate.CoordinateInt;
import Display.Color;
import Display.ColorfulChar;

/**
 * A class for bullet
 */
public class Bullet extends GameObject {

    public Bullet(CoordinateInt coordinate, CoordinateInt screenSize) {
        super(coordinate, screenSize);
    }

    public Bullet(CoordinateInt coordinate) {
        super(coordinate, null);
    }


    @Override
    protected ColorfulChar[][] generateAppearance() {
        return new ColorfulChar[][] {{new ColorfulChar('▶', Color.RED_BRIGHT)}};
    }

    @Override
    protected CoordinateInt generateSize() {
        return new CoordinateInt(1, 1);
    }

    @Override
    public void createBoundary() {
        createDefaultBoundary();
    }

}
