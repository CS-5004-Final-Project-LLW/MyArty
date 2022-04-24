package Object;

import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.GUI;

/**
 * An abstarct class for GameObject like Cannon, Target, Bullet and so on
 */
public abstract class GameObject {
    protected CoordinateInt coordinate;
    protected CoordinateInt boundary_min;
    protected CoordinateInt boundary_max;


    // subclass must impletment it to set a proper rectangle boundary for the object itself
    protected abstract void createBoundary();


    public CoordinateInt getBoundary_min() {
        return boundary_min;
    }

    public void setBoundary_min(CoordinateInt boundary_min) {
        this.boundary_min = boundary_min;
    }

    public CoordinateInt getBoundary_max() {
        return boundary_max;
    }

    public void setBoundary_max(CoordinateInt boundary_max) {
        this.boundary_max = boundary_max;
    }

    protected void createDefaultBoundary() {
        setBoundary_max(new CoordinateInt(GUI.WIDTH, GUI.HEIGHT));
        setBoundary_min(new CoordinateInt(0, 0));
    }
    protected void createNullBoundary() {
        setBoundary_max(null);
        setBoundary_min(null);
    }

    public CoordinateInt getCoordinate() {
        return new CoordinateInt(coordinate);
    }

    public void setCoordinate(CoordinateInt coordinate) {
        this.coordinate = new CoordinateInt(coordinate);
        legalizeCoordinate();
    }


    public GameObject(CoordinateInt coordinate) {
        /* generate proper boundary for object */
        createBoundary();
        if (boundary_max != null && boundary_min != null) {
            assert (boundary_max.x >= boundary_min.x);
            assert (boundary_max.y >= boundary_min.y);
        }

        /* set coordinate and size */
        setCoordinate(coordinate);
    }

    public int getX() {
        return coordinate.x;
    }

    public int getY() {
        return coordinate.y;
    }

    /**
     * Change the coordiante to a valid one
     * 
     * @param screenSize
     */
    public void legalizeCoordinate() {
        if (boundary_min != null) {
            if (coordinate.x < boundary_min.x) {
                coordinate.x = boundary_min.x;
            }
            if (coordinate.y < boundary_min.y) {
                coordinate.y = boundary_min.y;
            }
        }
        if (boundary_max != null) {
            if (coordinate.x > boundary_max.x) {
                coordinate.x = boundary_max.x;
            }

            if (coordinate.y > boundary_max.y) {
                coordinate.y = boundary_max.y;
            }
        }
    }

    public abstract boolean update();

    public abstract void draw(Graphics2D graph);

}
