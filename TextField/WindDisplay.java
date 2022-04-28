package TextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.Info;
import Main.Tools;
import Object.AbstractGameObject;

public class WindDisplay extends AbstractGameObject {

    public WindDisplay(CoordinateInt coordinate) {
        super(coordinate);
    }

    @Override
    public void draw(Graphics2D graph) {
        var font = new Font("Dialog", Font.BOLD, 40);

        Tools.drawStringWithOutline("Wind", getX() + 20, getY(), font, 15, Color.WHITE, Color.BLACK,
                graph);

        int windSpeed = (int) (Info.getWind() * 100);
        String windValue = "%d".formatted(Math.abs(windSpeed));
        Tools.drawStringWithOutline(windValue, getX() + 50, getY() + 50, font, 15, Color.WHITE,
                Color.BLACK, graph);
        if (windSpeed >= 1) {
            Tools.drawStringWithOutline(" →", getX() + 100, getY() + 50, font, 15, Color.WHITE,
                    Color.BLACK, graph);
        } else if (windSpeed <= -1) {
            Tools.drawStringWithOutline("← ", getX(), getY() + 50, font, 15, Color.WHITE,
                    Color.BLACK, graph);
        }
    }

    @Override
    public boolean update() {
        return true;
    }

}
