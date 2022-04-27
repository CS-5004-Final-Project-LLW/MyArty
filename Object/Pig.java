package Object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import Coordinate.CoordinateInt;
import Main.GUI;
import Main.Info;

/**
 * A class for Pig
 */
public class Pig extends AbstractGameObject {
    private int width;
    private int height;
    private int x = 300;
    private int xSpeed = 60;

    public Pig(CoordinateInt coordinate, int width, int height) {
        super(coordinate);
	    this.width = width;
        this.height = height;
    }

    @Override
    protected void createBoundary() {
        setBoundary_min(new CoordinateInt(0, 0));
        setBoundary_max(new CoordinateInt(GUI.WIDTH, GUI.HEIGHT));
    }


    @Override
    public void draw(Graphics2D graph) {
        BufferedImage image = Info.getPigImage();
        BufferedImage image_left = Info.getPigLeftImage();
        if (xSpeed >= 0) {
            graph.drawImage(image,x,200,200,170, null);
        } else {
            graph.drawImage(image_left,x,200,200,170, null);
        }
    }

    @Override
    public boolean update() {

        if (x >= GUI.WIDTH - 300) xSpeed = -xSpeed;
        if (x <= 0) xSpeed = -xSpeed;
        x += xSpeed / 10;

        return true;
    }

}
