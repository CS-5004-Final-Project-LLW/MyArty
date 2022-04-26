package Object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import Coordinate.CoordinateInt;
import Main.GUI;
import Main.Info;
import Main.Repo;

/**
 * A class for Target
 */
public class Target extends AbstractGameObject {
    private int width;
    private int height;

    private double preciseY;
    // TODO: need to refine
    private double speedY = -1;
    private final double GRAVITY = 10;

    public Target(CoordinateInt coordinate, int width, int height) {
        super(coordinate);
        this.width = width;
        this.height = height;
        this.preciseY = coordinate.y;

    }

    @Override
    protected void createBoundary() {
        // setBoundary_min(new CoordinateInt(GUI.WIDTH / 2 + 100, GUI.HEIGHT / 2 + 100));
        // setBoundary_max(new CoordinateInt(GUI.WIDTH - 100, GUI.HEIGHT - 100));
        createNullBoundary();
    }


    @Override
    public void draw(Graphics2D graph) {
        BufferedImage image = Info.getTargetImage();
        graph.drawImage(image, getX(), getY(), 100, 100, null);
    }

    @Override
    public boolean update() {
        if (Info.isFreezed()) {
            preciseY += speedY;
            coordinate.y = (int) preciseY;
            speedY += GRAVITY / GUI.getFps();
            if (getY() > GUI.HEIGHT) {
                return false;
            } else {
                return true;
            }
        } else {
            for (Bullet bullet : Repo.bullets) {
                double distanceX = (getX() + width / 2) - (bullet.getX() + bullet.getRadius());
                double distanceY = (getY() + height / 2) - (bullet.getY() + bullet.getRadius());
                double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
                if (distance < bullet.getRadius() + (width / 2 + height / 2) / 2) {
                    Repo.bullets.remove(bullet);
                    Info.Hit();
                    return true;
                }
            }
            return true;
        }

    }
}
