package Object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import Coordinate.CoordinateInt;
import Main.Info;

/**
 * A class for Heart
 */
public class Heart extends AbstractGameObject {
    private int width;
    private int height;
    private int distance;

    public Heart(CoordinateInt coordinate, int width, int height, int distance) {
        super(coordinate);
        this.width = width;
        this.height = height;
        this.distance = distance;
    }

    @Override
    protected void createBoundary() {
        createNullBoundary();
    }


    @Override
    public void draw(Graphics2D graph) {
        // draw red hearts
        BufferedImage heartImage = Info.getHeartImage();
        BufferedImage heartEmptyImage = Info.getHeartEmptyImage();

        for (int i = 0; i < Info.getLife(); i++) {
            graph.drawImage(heartImage, getX() + i * distance, getY(), width, height, null);
        }
        for (int i = Info.getLife(); i < Info.MAX_LIFE; i++) {
            graph.drawImage(heartEmptyImage, getX() + i * distance, getY(), width, height, null);
        }

        double threshold = 0.01;
        if (Math.abs(Info.getPreciseLife() - Info.getLife()) > threshold) {
            drawPartialHeart(graph, heartImage);
        }
    }

    private void drawPartialHeart(Graphics2D graph, BufferedImage heartImage) {
        double decimalLife = Info.getPreciseLife() - Info.getLife();
        int tempX = getX() + Info.getLife() * distance;
        int tempY = getY();
        int cutX = (int) (width * decimalLife);
        int cutY = height;

        /* Cut background */
        BufferedImage imageLeft = new BufferedImage(cutX, cutY, BufferedImage.TYPE_INT_ARGB);
        imageLeft.createGraphics().drawImage(heartImage, 0, 0, width, height, null);
        graph.drawImage(imageLeft, tempX, tempY, cutX, cutY, null);
    }


    @Override
    public boolean update() {
        return true;
    }

}
