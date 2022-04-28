package Button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.Info;
import Main.Tools;
import Object.AbstractGameObject;

public abstract class Button extends AbstractGameObject {

    protected int width;
    protected int height;
    protected String text;

    public Button(CoordinateInt coordinate, int width, int height, String text) {
        super(coordinate);
        this.width = width;
        this.height = height;
        this.text = text;
    }

    @Override
    protected void createBoundary() {
        createNullBoundary();
    }

    @Override
    public boolean update() {
        return true;
    }

    @Override
    public void draw(Graphics2D graph) {

        final int offset = 2;
        if (isMouseHovered()) {
            Tools.drawStringWithOutline(text, getX() + offset, getY(),
                    new Font("Comic Sans MS", Font.BOLD, 48), 12, Color.BLUE, Color.WHITE, graph);
        } else {
            Tools.drawStringWithOutline(text, getX(), getY(), new Font("Comic Sans MS", Font.BOLD, 48), 12,
                    Color.WHITE, Color.BLACK, graph);
        }

    }

    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }

    protected boolean isMouseHovered() {
        return Info.getCursorX() >= getX() && Info.getCursorX() <= getX() + width
                && Info.getCursorY() >= getY() && Info.getCursorY() <= getY() + height;
    }

    public boolean isClicked() {
        return Info.isClicking() && isMouseHovered();
    }



}
