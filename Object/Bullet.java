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
    // TODO: five parameters need to be refined
    private static final double SLOW_FACTOR = 1.5;
    private static final double AIR_RESISTANCE = 0.999;
    private static final double WIND_FACTOR = 0.5;
    private static final double GRAVITY = 0.5;
    private static final double VELOCITY_SCALE = 0.01;

    private double speedX;
    private double speedY;
    private double xPercent;
    private double yPercent;

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
        graph.drawImage(Info.bulletImage.get(), getBulletAt(), null);

    }

    @Override
    public boolean update() {
        setSpinAngle(spinAngle - 0.1);
        double timeInterval = (double) 1 / GUI.getFps() / SLOW_FACTOR;
        xPercent += speedX * timeInterval;
        yPercent += speedY * timeInterval;
        coordinate.x = (int) (xPercent * GUI.WIDTH);
        coordinate.y = (int) (yPercent * GUI.HEIGHT);
        speedY += GRAVITY * timeInterval;

        speedY *= AIR_RESISTANCE;
        speedX *= AIR_RESISTANCE;

        speedX += Info.getWind() * WIND_FACTOR * timeInterval;
        boolean isInside = getX() >= 0 && getX() <= GUI.WIDTH * 5 / 4 && getY() >= -GUI.HEIGHT / 2
                && getY() + radius <= GUI.HEIGHT;
        if (!isInside) {
            Info.miss();
        }
        return isInside;
    }

    public void setSpinAngle(double spinAngleDegree) {
        this.spinAngle = spinAngleDegree % 360;
    }


    private AffineTransform getBulletAt() {
        BufferedImage bulletImage = Info.bulletImage.get();
        AffineTransform at = new AffineTransform();
        at.translate(getX(), getY());

        double scaledFactor = (double) radius / (bulletImage.getHeight() / 2);
        at.scale(scaledFactor, scaledFactor);
        at.rotate(spinAngle, bulletImage.getWidth() / 2, bulletImage.getHeight() / 2);
        return at;
    }

}
