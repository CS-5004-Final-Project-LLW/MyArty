package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import Coordinate.CoordinateInt;
import Object.Cannon;
import Object.Target;

public class Tools {


    /**
     * Draw string with outlines
     * 
     * @param string contents
     * @param x position x
     * @param y position y
     * @param font font
     * @param scale scale factor, the larger, the smaller outline
     * @param outlineColor the color of outline
     * @param fontColor the color of contents
     * @param graph graphics
     */
    public static void drawStringWithOutline(String string, int x, int y, Font font, float scale,
            Color outlineColor, Color fontColor, Graphics2D graph) {
        FontRenderContext renderer = graph.getFontRenderContext();
        TextLayout textlayout = new TextLayout(string, font, renderer);
        Shape shape = textlayout.getOutline(AffineTransform.getTranslateInstance(x, y));

        RenderingHints hintsBackup = graph.getRenderingHints();

        graph.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graph.setColor(outlineColor);
        graph.setStroke(new BasicStroke(font.getSize2D() / scale));
        graph.draw(shape);
        graph.setColor(fontColor);
        graph.fill(shape);

        graph.setRenderingHints(hintsBackup);
    }

    /**
     * Freeze a thread for specified milliseconds
     * 
     * @param mills
     */
    static void sleepForMills(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read image from a file
     * 
     * @param fileName
     * @return
     */
    static BufferedImage loadImage(String fileName) {
        File targetImageFile = new File(fileName);
        BufferedImage image = null;
        try {
            image = ImageIO.read(targetImageFile);
        } catch (IOException e) {
            System.out.println(" Image file does not exist.");
            System.exit(-2);
        }
        return image;
    }

    /**
     * Create a cannon at a random position of the left screen
     *
     * @return Cannon
     */
    static Cannon generateCannon() {
        // x should be at the left screen
        int x = new Random().nextInt(GUI.WIDTH * 3 / 10);
        int y = GUI.HEIGHT * 4 / 5;
        Cannon cannon = new Cannon(new CoordinateInt(x, y), 150, 50, 80, 60);
        return cannon;
    }

    /**
     * Create a target at a random position of the right screen
     *
     * @return Target
     */
    static Target generateTarget() {
        // x should be at the right screen
        int x = GUI.WIDTH * 9 / 10 - new Random().nextInt(GUI.WIDTH * 3 / 10);
        int y = GUI.HEIGHT * 9 / 10 - new Random().nextInt(GUI.HEIGHT * 3 / 10);

        Target target = new Target(new CoordinateInt(x, y), 100, 100);
        return target;
    }
}
