package Object;

import java.awt.Graphics2D;
import Coordinate.CoordinateInt;

/**
 * A class for Explosion
 */
public class Explosion extends GameObject {

    public Explosion(CoordinateInt coordinate) {
        super(coordinate);
    }


    @Override
    protected void createBoundary() {
        createDefaultBoundary();
    }

    @Override
    public void draw(Graphics2D graph) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean update() {
        // TODO Auto-generated method stub
        return false;
    }

}
