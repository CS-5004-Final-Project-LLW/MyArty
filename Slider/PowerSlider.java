package Slider;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.Info;
import Main.Tools;

public class PowerSlider extends Slider {

    public PowerSlider(CoordinateInt coordinate, int width, int height) {
        super(coordinate, width, height);
        words = "Power";
        percentage = 0.5;
    }

    @Override
    public boolean update() {
        if (!super.update()) {
            return false;
        }

        Info.powerValue = (int) (100.0 * percentage);
        return true;

    }

    @Override
    public void draw(Graphics2D graph) {
        super.draw(graph);
        Font pf = new Font("Calibri", Font.LAYOUT_LEFT_TO_RIGHT, 30);

        /* Draw string */
        Tools.drawStringWithOutline(String.valueOf(Info.powerValue) + "%", getX() + width + 15,
                getY() + 15, pf, 15, Color.WHITE, Color.RED, graph);
    }
}
