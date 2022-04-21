package Object;

import java.awt.Graphics2D;
import Coordinate.CoordinateInt;

/**
 * A class for bullet
 */
public class BulletShadow extends GameObject {


    public BulletShadow(CoordinateInt coordinate) {
        super(coordinate);
    }


    @Override
    public void createBoundary() {
        createNullBoundary();
    }

    @Override
    public void draw(Graphics2D graph) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean update() {
        return true;
    }

}
