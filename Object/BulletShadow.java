package Object;

import Coordinate.CoordinateInt;
import Display.ColorfulChar;

/**
 * A class for bullet
 */
public class BulletShadow extends GameObject {


    public BulletShadow(CoordinateInt coordinate, CoordinateInt screenSize) {
        super(coordinate, screenSize);
    }

    public BulletShadow(CoordinateInt coordinate) {
        super(coordinate, null);
    }


    @Override
    protected ColorfulChar[][] generateAppearance() {
        return new ColorfulChar[][] {{new ColorfulChar('◼')}};
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
