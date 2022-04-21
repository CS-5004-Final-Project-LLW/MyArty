package Object;

import java.awt.Color;
import java.awt.Graphics2D;
import Coordinate.CoordinateInt;

/**
 * A class for bullet
 */
public class Bullet extends GameObject {


    public Bullet(CoordinateInt coordinate) {
        super(coordinate);
    }



    @Override
    public void createBoundary() {
        createNullBoundary();
    }

    @Override
    public void draw(Graphics2D graph) {
        graph.setColor(Color.GREEN);
        graph.fillOval(getX(), getY(), 50, 50);
    }

    @Override
    public boolean update() {
        // TODO Auto-generated method stub
        return true;
    }


}
