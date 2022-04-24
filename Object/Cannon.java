package Object;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.GUI;
import Main.Info;
import Main.Repo;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A class for Cannon
 */
public class Cannon extends GameObject {
    /* A special number for gravity after a whole-night testing */
    CoordinateInt size;
    private int width;
    private int height;


    public Cannon(CoordinateInt coordinate, int width, int height) {
        super(coordinate);
        this.width = width;
        this.height = height;
    }

    /**
     * Convert degree angle to radian
     * 
     * @param radian
     * @return double radian
     */
    private double RadianToDegree(double radian) {
        return radian / Math.PI * 180;
    }

    @Override
    protected void createBoundary() {
        setBoundary_min(new CoordinateInt(width, GUI.HEIGHT / 2 + height));
        setBoundary_max(new CoordinateInt(GUI.WIDTH / 2 - width, GUI.HEIGHT - height));
    }



    @Override
    public boolean update() {

        int centerX = getX() + width / 2;
        int centerY = getY() + height / 2;

        /* Update angle */
        double dy = Info.getCursorY() - centerY;
        double dx = Info.getCursorX() - centerX;

        double radian = Math.atan2(dy, dx);
        Info.angleValue = (int) RadianToDegree(radian);

        /* Create bullets */
        int range = 400;
        if (Info.isClicking() && Repo.isReadyForShot() && Math.sqrt(dx * dx + dy * dy) < range) {
            CoordinateInt bulletPoint = new CoordinateInt(centerX, centerY);
            Repo.bullets.add(new Bullet(bulletPoint, Info.powerValue, Info.angleValue, 25));
        }

        return true;
    }


    @Override
    public void draw(Graphics2D graph) {
        BufferedImage image = Info.getCannonImage();

        Graphics2D graphicsImage = (Graphics2D) image.getGraphics();
        graphicsImage.setBackground(Color.BLACK);
        graph.drawImage(image, getX(), getY(), width, height, null);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }


}
