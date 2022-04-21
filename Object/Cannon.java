package Object;

import java.awt.Graphics2D;
import java.util.ArrayList;
import Coordinate.CoordinateDouble;
import Coordinate.CoordinateInt;
import Main.GUI;
import Main.Info;
import Main.Repo;
import java.awt.Color;

/**
 * A class for Cannon
 */
public class Cannon extends GameObject {
    /* A special number for gravity after a whole-night testing */
    private static double GRAVITY = 0.5;
    private static double VELOCITY_SCALE = 0.01;
    CoordinateInt size;


    public Cannon(CoordinateInt coordinate) {
        super(coordinate);
    }


    @Override
    protected void createBoundary() {
        setBoundary_min(new CoordinateInt(1, GUI.HEIGHT / 2));
        setBoundary_max(new CoordinateInt(GUI.WIDTH / 2 - 1, GUI.HEIGHT - 100));
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


    /**
     * Calculate the position(coordinate) of bullet at a specific time
     * 
     * @param time in millisecond
     * @param angleDegree between 0 and 90 degree
     * @param powerPercentage between 0 to 100
     * @return CoordinateDouble between 0 and 1
     */
    private CoordinateDouble getCurrentCoordinate(double time, double angleDegree,
            double powerPercentage, CoordinateInt screenSize) {
        /* x0, y0, x, y are all percentages (from 0 to 1) */
        int screenSizeX = screenSize.x;
        int screenSizeY = screenSize.y;
        double v = (double) powerPercentage * VELOCITY_SCALE;
        double t = (double) time / 1000;
        double x0 = (double) getX() / screenSizeX;
        double y0 = (double) getY() / screenSizeY;
        double theta = degreeToRadian(angleDegree);

        // x = x0 + v * cos(θ) * t
        double x = x0 + v * Math.cos(theta) * t;
        // y = y0 + v * sin(θ) * t - 1/2 * g * t^2
        double y = y0 + v * Math.sin(theta) * t - GRAVITY * t * t / 2;

        // return the result as a coordinate
        return new CoordinateDouble(x, y);
    }


    /**
     * Calculate the whole trace of bullet from being shot to reaching the same level of target
     * 
     * @param angleDegree between 0 and 90 degree
     * @param powerPercentage between 0 to 100
     * @param target the target
     * @param screenSize the size of display screen
     * @return ArrayList<CoordinateInt>
     */
    public ArrayList<CoordinateInt> getShootTrace(double angleDegree, double powerPercentage,
            GameObject target, CoordinateInt screenSize) {
        ArrayList<CoordinateInt> result = new ArrayList<CoordinateInt>();
        /* x, y are both percentages (from 0 to 1) */
        int screenSizeX = screenSize.x;
        int screenSizeY = screenSize.y;
        double time = 0;
        double x = getX();
        double y = getY();
        double xPercent = x / screenSizeX;
        double yPercent = y / screenSizeY;
        CoordinateDouble currentCoordinate = new CoordinateDouble(getCoordinate());

        // in loop if time and bullet is not out of limit
        while (time <= 5000 && yPercent >= target.getY() / screenSizeY && 0 <= xPercent
                && xPercent <= 1 && 0 <= yPercent && yPercent <= 1.2) {

            // renew the coordinate of bullet
            currentCoordinate =
                    getCurrentCoordinate(time, angleDegree, powerPercentage, screenSize);
            xPercent = currentCoordinate.x;
            yPercent = currentCoordinate.y;
            int resultX = (int) (xPercent * screenSizeX);
            int resultY = (int) (yPercent * screenSizeY);

            // add new coordinate to the arraylist of result
            result.add(new CoordinateInt(resultX, resultY));

            // add time by 100 ms
            time += 100;
        }

        return result;
    }


    /**
     * Calculate if the bullet from cannon hit the target finally
     * 
     * @param angleDegree between 0 and 90 degree
     * @param powerPercentage between 0 to 100
     * @param target the target
     * @param screenSize the size of display screen
     * @return boolean {@code true} if the bullet hit the target in the end
     */
    public boolean getShootResult(double angleDegree, double powerPercentage, GameObject target,
            CoordinateInt screenSize) {
        double v = powerPercentage / 100;
        double h = (double) getY() / screenSize.y;
        double x0 = (double) getX() / screenSize.x;
        double y0 = (double) getY() / screenSize.y;
        double theta = degreeToRadian(angleDegree);
        double x = (double) target.getX() / screenSize.x;

        // x = x0 + v * cos(θ) * t
        // Therefore, t = (x - x0) / * (v * cos(θ))
        // calculate the time when the bullet is at the same x value with target
        double t = (x - x0) / v / Math.cos(theta);

        // TODO: need a careful design
        double y = y0 + h + v * Math.sin(theta) * t - GRAVITY * t * t / 2;

        // return true if the y value of the bullet is between the top and botton line of the target
        // return Math.abs(y * screenSize.y - target.getY()) <= target.size.y;
        return false;
    }


    @Override
    public boolean update() {
        if (Repo.fireButton.isPressed()) {
            Repo.bullets.add(new Bullet(new CoordinateInt(getX() + 200, getY() - 200)));
        }
        return true;
    }


    @Override
    public void draw(Graphics2D graph) {
        graph.setColor(Color.black);
        graph.fillOval(getX(), getY(), GUI.WIDTH / 8, GUI.WIDTH / 8);

    }


}
