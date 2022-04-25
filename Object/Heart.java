package Object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import Coordinate.CoordinateInt;
import Main.GUI;
import Main.Info;
import Main.Repo;

/**
 * A class for Heart
 */
public class Heart extends GameObject {
    private int width;
    private int height;

    public Heart(CoordinateInt coordinate, int width, int height) {
        super(coordinate);
	    this.width = width;
        this.height = height;
    }

    @Override
    protected void createBoundary() {
        setBoundary_min(new CoordinateInt(GUI.WIDTH/2 + 30, 25));
        setBoundary_max(new CoordinateInt(GUI.WIDTH/2 + 30, 25));
    }


    @Override
    public void draw(Graphics2D graph) {
        // draw red hearts
        BufferedImage heartImage = Info.getHeartImage();
        int x = getX();
        int i = 0;
        BufferedImage heartEmptyImage = Info.getHeartEmptyImage();
        for(i = 0; i < Info.getLife(); i++){
            graph.drawImage(heartImage, x, getY(),50, 50,null);
            x+=60;
        }
        for(i = Info.getLife(); i < 5; i++){
            graph.drawImage(heartEmptyImage, x, getY(),50,50,null);
            x += 60;
        }
    }

    @Override
    public boolean update() {
        return false;
    }

}
