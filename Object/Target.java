package Object;

import java.awt.Color;
import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.GUI;
import Main.Repo;

/**
 * A class for Target
 */
public class Target extends GameObject {

    public Target(CoordinateInt coordinate) {
        super(coordinate);
    }

    @Override
    protected void createBoundary() {
        setBoundary_min(new CoordinateInt(GUI.WIDTH / 2 + 100, GUI.HEIGHT / 2 + 100));
        setBoundary_max(new CoordinateInt(GUI.WIDTH - 100, GUI.HEIGHT - 100));
    }


    @Override
    public void draw(Graphics2D graph) {
        graph.setColor(Color.BLACK);
        graph.fillOval(getX(), getY(), 100, 100);

    }

    @Override
    public boolean update() {
        for (Bullet bullet : Repo.bullets) {
            double distanceX = getX() + 50 - bullet.getX();
            double distanceY = getY() + 50 - bullet.getY();
            if (Math.sqrt(distanceX * distanceX + distanceY * distanceY) <= 100) {
                Repo.bullets.remove(bullet);
                return false;
            }
        }
        return true;
    }

}
