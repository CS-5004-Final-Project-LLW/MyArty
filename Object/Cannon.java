package Object;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import Coordinate.CoordinateInt;
import Main.Info;
import Main.Repo;
import Main.Tools;

/** A class for Cannon */
public class Cannon extends AbstractGameObject {
    private final Base base;
    private final Barrel barrel;
    private final int BULLET_HEIGHT = 35;


    public Cannon(CoordinateInt coordinate, int cannonWidth, int cannonBaseWidth) {
        super(coordinate);
        /* Create cannon barrel */
        this.barrel = new Barrel(coordinate, cannonWidth, Info.cannonImage.get());

        /* Create cannon base */
        final int baseX = getX() + barrel.height / 2 - cannonBaseWidth / 2;
        final int baseY = getY() + barrel.height - cannonBaseWidth / 2;
        this.base = new Base(new CoordinateInt(baseX, baseY), cannonBaseWidth + 2,
                Info.cannonBaseImage.get());
    }


    @Override
    public boolean update() {

        final int centerX = getX() + barrel.height / 2;
        final int centerY = getY() + barrel.height / 2;

        /* Update angle */
        final double dy = Info.cursorY.get() - centerY;
        final double dx = Info.cursorX.get() - centerX;

        double radian = Math.atan2(dy, dx);
        // TODO: angleValue should be valid too
        Info.angleValue = (int) Tools.radianToDegree(radian);
        if (-Tools.radianToDegree(radian) > 90.0) {
            Info.setRotateDegree(Tools.degreeToRadian(-90));
        } else if (-Tools.radianToDegree(radian) < 0) {
            Info.setRotateDegree(0);
        } else {
            Info.setRotateDegree(radian);
        }

        /* Create bullets */
        int range = 400;
        if (Info.clicking.get() && Repo.isReadyForShot() && Math.sqrt(dx * dx + dy * dy) < range) {
            final int barrelLength = barrel.width - barrel.height / 2 + BULLET_HEIGHT / 2;
            final double bulletX =
                    centerX + barrelLength * Math.cos(Info.getRotateDegree()) - BULLET_HEIGHT;
            final double bulletY =
                    centerY + barrelLength * Math.sin(Info.getRotateDegree()) - BULLET_HEIGHT;
            CoordinateInt bulletPoint = new CoordinateInt(bulletX, bulletY);
            Repo.bullets
                    .add(new Bullet(bulletPoint, Info.powerValue, Info.angleValue, BULLET_HEIGHT));
        }

        return barrel.update() && base.update();
    }

    @Override
    public void draw(Graphics2D graph) {
        barrel.draw(graph);
        base.draw(graph);
    }

}


class Barrel extends ScaledGameObject {

    public Barrel(CoordinateInt coordinate, int width, BufferedImage image) {
        super(coordinate, width, image);
    }


    @Override
    public void draw(Graphics2D graph) {
        BufferedImage cannonImage = Info.cannonImage.get();

        /* transform the image of cannon body */
        AffineTransform at = new AffineTransform();
        at.translate(getX(), getY());
        at.rotate(Info.getRotateDegree(), height / 2, height / 2);
        at.scale(scaledFactor, scaledFactor);

        graph.drawImage(cannonImage, at, null);
    }


    @Override
    public boolean update() {
        return true;
    }

}


class Base extends ScaledGameObject {

    public Base(CoordinateInt coordinate, int width, BufferedImage image) {
        super(coordinate, width, image);
    }

    @Override
    public void draw(Graphics2D graph) {
        BufferedImage cannonBaseImage = Info.cannonBaseImage.get();
        graph.drawImage(cannonBaseImage, getX(), getY(), width, width + 2, null);
    }

    @Override
    public boolean update() {
        return true;
    }

}


abstract class ScaledGameObject extends AbstractGameObject {
    protected int width;
    protected int height;
    protected double scaledFactor;

    public ScaledGameObject(CoordinateInt coordinate, int width, BufferedImage image) {
        super(coordinate);
        this.width = width;

        /* Calculate height and scaled factor */
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        this.scaledFactor = (double) width / originalWidth;
        this.height = (int) (originalHeight * scaledFactor);
    }

}
