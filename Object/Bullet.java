package Object;

import java.awt.Color;
import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.GUI;

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
        graph.setColor(Color.GREEN);
        graph.fillOval(getX(), getY(), 50, 50);
    }

    @Override
    public boolean update() {
        double timeInterval = (double) 1 / GUI.getFps();
        xPercent += speedX * timeInterval;
        yPercent += speedY * timeInterval;
        coordinate.x = (int) (xPercent * GUI.WIDTH);
        coordinate.y = (int) (yPercent * GUI.HEIGHT);
        speedY += GRAVITY * timeInterval;
        // System.out.println(xPercent + " " + yPercent + " " + +coordinate.x + " " + coordinate.y
        // + " " + speedY);
        return true;
    }


}
