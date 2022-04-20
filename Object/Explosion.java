package Object;

import Coordinate.CoordinateInt;
import Display.Color;
import Display.ColorfulChar;

/**
 * A class for Explosion
 */
public class Explosion extends GameObject {

    public Explosion(CoordinateInt coordinate) {
        super(coordinate, null);
    }

    @Override
    protected ColorfulChar[][] generateAppearance() {
        return new ColorfulChar[][] {{new ColorfulChar('*', Color.RED_BRIGHT)}};
    }

    @Override
    protected CoordinateInt generateSize() {
        return new CoordinateInt(1, 1);
    }


    @Override
    protected void createBoundary() {
        createDefaultBoundary();
    }
}
