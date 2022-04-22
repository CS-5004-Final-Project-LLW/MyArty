package Object;

import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.GUI;
import Main.Info;
import Main.Repo;
import java.awt.Color;

/**
 * A class for Cannon
 */
public class Cannon extends GameObject {
    /* A special number for gravity after a whole-night testing */
    CoordinateInt size;
    private int width = GUI.WIDTH / 8;
    private int height = GUI.HEIGHT / 8;


    public Cannon(CoordinateInt coordinate) {
        super(coordinate);
    }

    public Cannon(CoordinateInt coordinate, int width, int height) {
        super(coordinate);
        this.width = width;
        this.height = height;
    }


    @Override
    protected void createBoundary() {
        setBoundary_min(new CoordinateInt(100, GUI.HEIGHT / 2));
        setBoundary_max(new CoordinateInt(GUI.WIDTH / 2 - 100, GUI.HEIGHT - 100));
    }



    @Override
    public boolean update() {
        if (Repo.fireButton.isPressed()) {
            CoordinateInt bulletPoint = new CoordinateInt(getX(), getY());
            Repo.bullets.add(new Bullet(bulletPoint, Info.powerValue, Info.angleValue));
        }
        return true;
    }


    @Override
    public void draw(Graphics2D graph) {
        graph.setColor(Color.black);
        graph.fillOval(getX(), getY(), width, height);
    }


}
