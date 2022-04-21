package Object;

import java.awt.Color;
import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.Info;

public class Button extends GameObject {

    private int width;
    private int height;

    public Button(CoordinateInt coordinate, int width, int height) {
        super(coordinate);
        this.width = width;
        this.height = height;
    }

    @Override
    protected void createBoundary() {
        setBoundary_max(null);
        setBoundary_min(null);
    }

    @Override
    public boolean update() {
        return true;
    }

    @Override
    public void draw(Graphics2D graph) {
        graph.setColor(Color.blue);
        graph.fillRect(coordinate.x, coordinate.y, 100, 40);

    }

    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }


    public boolean isPressed() {
        return Info.isClicking() && Info.getCursorX() >= getX()
                && Info.getCursorX() <= getX() + width && Info.getCursorY() >= getY()
                && Info.getCursorY() <= getY() + height;
    }

}
