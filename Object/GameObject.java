package Object;

import java.awt.Graphics2D;

/**
 * An interface for GameObject like Cannon, Target, Bullet and so on
 */
public interface GameObject {
    int getX();

    int getY();

    boolean update();

    void draw(Graphics2D graph);

}

