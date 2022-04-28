package Slider;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.Info;
import Main.Tools;
import Object.AbstractGameObject;

public abstract class Slider extends AbstractGameObject {
    protected int width;
    protected int height;

    protected boolean grapped = false;
    protected double percentage;
    protected int barWidth = 50;
    protected int barHeight = 50;
    protected String words;

    public Slider(CoordinateInt coordinate, int width, int height) {
        super(coordinate);
        this.width = width;
        this.height = height;
    }


    @Override
    protected void createBoundary() {
        createNullBoundary();
    }

    private boolean isCursorInside() {
        return Info.cursorX.get() > getX() - barWidth / 2
                && Info.cursorX.get() < getX() + width + barWidth / 2
                && Info.cursorY.get() > getY() + height / 2 - barHeight / 2 + 30
                && Info.cursorY.get() < getY() + height / 2 + barHeight / 2 + 30;
    }


    @Override
    public boolean update() {
        if ((Info.pressed.get() || Info.dragging.get()) && isCursorInside()) {
            grapped = true;
        }
        if (!(Info.pressed.get() || Info.dragging.get())) {
            grapped = false;
        }

        if (grapped) {
            double temp = ((double) Info.cursorX.get() - getX()) / width;
            temp = Math.max(0, temp);
            percentage = Math.min(1, temp);
        }

        return true;
    }

    @Override
    public void draw(Graphics2D graph) {
        /* Draw slider */
        graph.setColor(Color.BLUE);
        graph.fillRect(getX(), getY()+30, width, height);

        /* Draw bar */
        graph.setColor(Color.BLACK);
        int barX = (int) (percentage * width) + getX() - barWidth / 2;
        int barY = getY() + height / 2 - barHeight / 2;
        graph.drawImage(Info.getSliderImage(), barX, barY + 30, barWidth, barHeight, null);

        /* Draw string */
        Tools.drawStringWithOutline(words, getX(), getY()-40, new Font("Dialog", Font.BOLD, 40),
        15, Color.WHITE, Color.BLACK, graph);
  }
}
