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
        return Info.getCursorX() > getX() - barWidth / 2
                && Info.getCursorX() < getX() + width + barWidth / 2
                && Info.getCursorY() > getY() + height / 2 - barHeight / 2 + 30
                && Info.getCursorY() < getY() + height / 2 + barHeight / 2 + 30;
    }


    @Override
    public boolean update() {
        if ((Info.isPressed() || Info.isDragging()) && isCursorInside()) {
            grapped = true;
        }
        if (!(Info.isPressed() || Info.isDragging())) {
            grapped = false;
        }

        if (grapped) {
            double temp = ((double) Info.getCursorX() - getX()) / width;
            temp = Math.max(0, temp);
            percentage = Math.min(1, temp);
        }

        return true;
    }

    @Override
    public void draw(Graphics2D graph) {
        /* Draw slider */
        //graph.setColor(Color.BLUE);
        graph.drawImage(Info.getSliderBarImage(),getX()-40, getY()+30, width + 70, height,null);
        //graph.fillRect(getX(), getY()+30, width, height);

        /* Draw bar */
        graph.setColor(Color.BLACK);
        int barX = (int) (percentage * width) + getX() - barWidth / 2;
        int barY = getY() + height / 2 - barHeight / 2;
        graph.drawImage(Info.getSliderImage(), barX, barY+30, barWidth, barHeight, null);

    /* Draw string */
    Tools.drawStringWithOutline(
        words,
        getX(),
        getY()-40 ,
        new Font("Calibri", Font.LAYOUT_LEFT_TO_RIGHT, 40),
        20,
        Color.WHITE,
        Color.BLACK,
        graph);
  }
}
