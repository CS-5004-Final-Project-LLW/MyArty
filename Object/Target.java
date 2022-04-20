package Object;

import Coordinate.CoordinateInt;
import Display.Color;
import Display.ColorfulChar;

/**
 * A class for Target
 */
public class Target extends GameObject {

    public Target(CoordinateInt coordinate, CoordinateInt screenSize) {
        super(coordinate, screenSize);
    }

    @Override
    protected void createBoundary() {
        setBoundary_min(new CoordinateInt(getScreenSize().x / 2 + 1, 1));
        setBoundary_max(new CoordinateInt(getScreenSize().x - 2, getScreenSize().y / 2 - 1));
    }

    @Override
    protected ColorfulChar[][] generateAppearance() {
        ColorfulChar[][] appearance = new ColorfulChar[3][3];
        for (int i = 0; i < appearance.length; i++) {
            for (int j = 0; j < appearance[i].length; j++) {
                appearance[i][j] = new ColorfulChar('⬢', Color.YELLOW_BRIGHT);
            }
        }
        return appearance;
    }


    @Override
    protected CoordinateInt generateSize() {
        return new CoordinateInt(3, 3);
    }
}
