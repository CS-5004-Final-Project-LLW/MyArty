package Object;

import Main.Tools;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import Coordinate.CoordinateInt;
import Main.DebugInfo;
import Main.GUI;
import Main.Info;
import java.awt.geom.AffineTransform;

/**
 * A class for bullet
 */
public class Bullet extends AbstractGameObject {
    private static final double SLOW_FACTOR = 1.5;
    private static final double AIR_RESISTANCE = 0.999;

    private double speedX;
    private double speedY;
    private double xPercent;
    private double yPercent;
    private static double GRAVITY = 0.5;
    private static double VELOCITY_SCALE = 0.01;
    private int radius;
    private double spinAngle;

    public int getRadius() {
        return radius;
    }

    public Bullet(CoordinateInt coordinate, double power, double degree, int radius) {
        super(coordinate);
        xPercent = (double) coordinate.x / GUI.WIDTH;
        yPercent = (double) coordinate.y / GUI.HEIGHT;
        double velocity = VELOCITY_SCALE * power;
        this.speedX = velocity * Math.cos(Tools.degreeToRadian(degree));
        this.speedY = velocity * Math.sin(Tools.degreeToRadian(degree));
        this.radius = radius;
        if (DebugInfo.isDebugging()) {
            System.out.println("New Bullet %d %d %.2f %.2f %d".formatted(coordinate.x, coordinate.y,
                    power, degree, radius));
        }
    }

    @Override
    public void createBoundary() {
        createNullBoundary();
    }

    @Override
    public void draw(Graphics2D graph) {
        BufferedImage bulletImage = Info.getBulletImage();
        var tempImage = new BufferedImage(bulletImage.getWidth(), bulletImage.getHeight(),
                bulletImage.getType());
        tempImage.createGraphics().drawImage(bulletImage, getRotation(), null);

        graph.drawImage(tempImage, getX(), getY(), 70, 70, null);

    }

    @Override
    public boolean update() {
        setSpinAngle(spinAngle - 0.1);
        // TODO: what about making bullets slower
        double timeInterval = (double) 1 / GUI.getFps() / SLOW_FACTOR;
        xPercent += speedX * timeInterval;
        yPercent += speedY * timeInterval;
        coordinate.x = (int) (xPercent * GUI.WIDTH);
        coordinate.y = (int) (yPercent * GUI.HEIGHT);
        speedY += GRAVITY * timeInterval;
        speedY *= AIR_RESISTANCE;
        speedX *= AIR_RESISTANCE;
        boolean isInside = coordinate.x >= 0 && coordinate.x <= GUI.WIDTH && coordinate.y >= 0
                && coordinate.y <= GUI.HEIGHT - 150;
        if (!isInside) {
            Info.miss();
        }
        return isInside;
    }

    public void setSpinAngle(double spinAngleDegree) {
        this.spinAngle = spinAngleDegree % 360;
    }


    private AffineTransform getRotation() {
        BufferedImage bulletImage = Info.getBulletImage();
        AffineTransform at = new AffineTransform();
        at.rotate(spinAngle, bulletImage.getWidth() / 2, bulletImage.getHeight() / 2);
        return at;
    }

}
