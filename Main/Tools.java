package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.RenderingHints;

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

}
