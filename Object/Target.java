package Object;

import java.awt.Color;
import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.GUI;
import Main.Repo;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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

        File targetImageFile = new File("target.png");
        BufferedImage image = null;
        try{
            image = ImageIO.read(targetImageFile);
        } catch (IOException e) {
            System.out.println(" Image file does not exist.");
            System.exit(-2);
        }
        graph.drawImage(image,getX(),getY(),100,100,null);
//        graph.setColor(Color.BLACK);
//        graph.fillOval(getX(), getY(), 100, 100);


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
