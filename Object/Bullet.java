package Object;

import java.awt.Color;
import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.GUI;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A class for bullet
 */
public class Bullet extends GameObject {
    private double speedX;
    private double speedY;
    private double xPercent;
    private double yPercent;
    private static double GRAVITY = 0.5;
    private static double VELOCITY_SCALE = 0.01;

    public Bullet(CoordinateInt coordinate, double power, double degree) {
        super(coordinate);
        xPercent = (double) coordinate.x / GUI.WIDTH;
        yPercent = (double) coordinate.y / GUI.HEIGHT;
        double velocity = VELOCITY_SCALE * power;
        this.speedX = velocity * Math.cos(degreeToRadian(degree));
        this.speedY = -velocity * Math.sin(degreeToRadian(degree));
    }

    /**
     * Convert degree angle to radian
     * 
     * @param degree
     * @return double radian
     */
    private double degreeToRadian(double degree) {
        return degree * Math.PI / 180;
    }


    @Override
    public void createBoundary() {
        createNullBoundary();
    }

    @Override
    public void draw(Graphics2D graph) {
        File bulletImageFile = new File("bullet.png");
        BufferedImage image = null;
        try{
         image = ImageIO.read(bulletImageFile);
        } catch (IOException e) {
            System.out.println(" Image file does not exist.");
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            System.exit(-2);
        }

        graph.drawImage(image,getX(),getY(),70,50,null);
    }

    @Override
    public boolean update() {
        double timeInterval = (double) 1 / GUI.getFps();
        xPercent += speedX * timeInterval;
        yPercent += speedY * timeInterval;
        coordinate.x = (int) (xPercent * GUI.WIDTH);
        coordinate.y = (int) (yPercent * GUI.HEIGHT);
        speedY += GRAVITY * timeInterval;
        return coordinate.x >= -100 && coordinate.x <= GUI.WIDTH + 100 && coordinate.y >= -100
                && coordinate.y <= GUI.HEIGHT + 100;
    }




}
