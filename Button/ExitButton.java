package Button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.Tools;

public class ExitButton extends Button {



    public ExitButton(CoordinateInt coordinate, int width, int height, String text) {
        super(coordinate, width, height, text);
    }

    @Override
    public boolean update() {
        if (isClicked()) {
            System.exit(0);
        }
        return true;
    }

    @Override
    public void draw(Graphics2D graph) {
        super.draw(graph);
    }
}
