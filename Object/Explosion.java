package Object;

import java.awt.Graphics2D;
import Coordinate.CoordinateInt;

/**
 * A class for Explosion
 */
public class Explosion extends AbstractGameObject {

    public Explosion(CoordinateInt coordinate) {
        super(coordinate);
    }


    @Override
    protected void createBoundary() {
        createNullBoundary();
    }

    @Override
    public void draw(Graphics2D graph) {

    }

    @Override
    public boolean update() {
        return true;
    }

}
