package Slider;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics2D;

import Coordinate.CoordinateInt;
import Main.Info;
import Object.GameObject;

public abstract class Slider extends GameObject {
    protected int width;
    protected int height;

    protected boolean grapped = false;
    protected double percentage;
    protected int barWidth = 15;
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
                && Info.getCursorY() > getY() + height / 2 - barHeight / 2
                && Info.getCursorY() < getY() + height / 2 + barHeight / 2;
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
            double temp = ((double) Info.getCursorX() - getX()) / 100;
            temp = Math.max(0, temp);
            percentage = Math.min(1, temp);
        }

        return true;
    }

    @Override
    public void draw(Graphics2D graph) {
        graph.setColor(Color.GRAY);
        graph.fillRect(getX(), getY(), width, height);

        graph.setColor(Color.BLACK);
        int barX = (int) (percentage * width) + getX() - barWidth / 2;
        int barY = getY() + height / 2 - barHeight / 2;
        graph.fillRect(barX, barY, barWidth, barHeight);

        graph.setColor(Color.BLACK);
        Font f = new Font("Calibri", Font.BOLD, 25);
        graph.setFont(f);
        graph.drawString(words, getX(), getY() - height);
    }
}
