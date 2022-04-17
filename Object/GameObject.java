package Object;

/**
 * An abstarct class for GameObject like Cannon, Target, Bullet and so on
 */
public abstract class GameObject {
    private CoordinateInt coordinate;
    private CoordinateInt size; // rectangle
    private CoordinateInt screenSize;
    private CoordinateInt boundary_min;
    private CoordinateInt boundary_max;

    // subclass must impletment it to set a proper rectangle boundary for the object itself
    protected abstract void createBoundary();

    public CoordinateInt getScreenSize() {
        return screenSize;
    }


    public void setScreenSize(CoordinateInt screenSize) {
        this.screenSize = screenSize;
    }

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
        if (screenSize != null) {
            setBoundary_min(new CoordinateInt(0, 0));
            setBoundary_max(new CoordinateInt(screenSize.x - 1, screenSize.y - 1));
        } else {
            setBoundary_max(null);
            setBoundary_max(null);
        }
    }


    public CoordinateInt getCoordinate() {
        return new CoordinateInt(coordinate);
    }

    public void setCoordinate(CoordinateInt coordinate) {
        this.coordinate = new CoordinateInt(coordinate);
        legalizeCoordinate();
    }

    public CoordinateInt getSize() {
        return new CoordinateInt(size);
    }

    public void setSize(CoordinateInt size) {
        this.size = new CoordinateInt(size);
    }

    public GameObject(CoordinateInt coordinate, CoordinateInt size, CoordinateInt screenSize) {
        /* set screen size at the beginning */
        this.screenSize = screenSize;

        /* generate proper boundary for object */
        createBoundary();
        if (boundary_max != null && boundary_min != null) {
            assert (boundary_max.x >= boundary_min.x);
            assert (boundary_max.y >= boundary_min.y);
        }

        /* set coordinate and size */
        setCoordinate(coordinate);
        setSize(size);
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

    // /**
    // * @param screenSize
    // * @return boolean {@code ture} if the coordinate is valid according to the screen size
    // */
    // public boolean isValid(CoordinateInt screenSize) {
    // return coordinate.x >= 0 && coordinate.x < screenSize.x && coordinate.y >= 0
    // && coordinate.y < screenSize.y;
    // }



}
