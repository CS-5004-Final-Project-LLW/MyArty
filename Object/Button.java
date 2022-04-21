package Object;

import java.awt.Color;
import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.Info;

public abstract class Button extends GameObject {

    protected int width;
    protected int height;

    public Button(CoordinateInt coordinate, int width, int height) {
        super(coordinate);
        this.width = width;
        this.height = height;
    }

    @Override
    protected void createBoundary() {
        createNullBoundary();
    }

    @Override
    public abstract boolean update();

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
