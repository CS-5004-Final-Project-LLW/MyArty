package Button;

import Coordinate.CoordinateInt;
import Main.GUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class ExitButton extends Button {

    public ExitButton(CoordinateInt coordinate, int width, int height) {
        super(coordinate, width, height);
    }

    @Override
    public boolean update() {
        if (isPressed()) {
            System.exit(0);
        }
        return true;
    }
    @Override
    public void draw(Graphics2D graph) {
        super.draw(graph);
        graph.setColor(Color.white);
        Font f = new Font("Calibri", Font.BOLD, 25);
        graph.setFont(f);
        graph.drawString("reset",coordinate.x+20,coordinate.y+25);
    }


}
