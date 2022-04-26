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
import javax.imageio.ImageIO;
import Coordinate.CoordinateAndSize;
import Object.Heart;


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
        final int magicNumber = 50; // I do not know why but it works
        Shape shape =
                textlayout.getOutline(AffineTransform.getTranslateInstance(x, y + magicNumber));

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
     * Generate a graph from specified image with rendering hints
     * 
     * @param image a BufferImage
     * @return a graph from specified image with rendering hints
     */
    public static Graphics2D generateGraph(BufferedImage image) {
        Graphics2D graph = (Graphics2D) image.getGraphics();
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // graph.setRenderingHint(RenderingHints.KEY_RENDERING,
        // RenderingHints.VALUE_RENDER_QUALITY);
        return graph;
    }

    /**
     * Convert degree radian to angle
     *
     * @param radian
     * @return double degree
     */
    public static double radianToDegree(double radian) {
        return radian / Math.PI * 180;
    }

    /**
     * Convert degree angle to radian
     *
     * @param degree
     * @return double radian
     */
    public static double degreeToRadian(double degree) {
        return degree * Math.PI / 180;
    }


    public static CoordinateAndSize scaledImage(double scaleFactor, CoordinateAndSize cas) {
        int x = cas.x;
        int y = cas.y;
        int width = cas.width;
        int height = cas.height;
        int newX = (int) (x + width / 2 * (1 - scaleFactor));
        int newY = (int) (y + height / 2 * (1 - scaleFactor));
        int newWidth = (int) (width * scaleFactor);
        int newHeight = (int) (height * scaleFactor);
        return new CoordinateAndSize(newX, newY, newWidth, newHeight);
    }

    public void printStackTrace() {
        try {
            throw new IllegalStateException();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}


