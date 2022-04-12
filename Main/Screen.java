package Main;

import Object.CoordinateInt;
import Object.GameObject;

public class Screen {
    private final CoordinateInt screenSize;
    private int[][] buffer;

    public CoordinateInt getScreenSize() {
        return screenSize;
    }

    public Screen(CoordinateInt screenSize) {
        this.screenSize = screenSize;
        this.buffer = new int[screenSize.x][screenSize.y];
    }

    private void paintPoint(CoordinateInt dot, int value) {
        int x = dot.x;
        int y = dot.y;
        if (x < 0 || x >= screenSize.x || y < 0 || y >= screenSize.y) {
            return;
        }

        buffer[x][y] = value;
    }

    public void addObject(GameObject gameObject) {
        CoordinateInt size = gameObject.getSize();
        CoordinateInt coordinate = gameObject.getCoordinate();

        // System.out.println("addObject " + size.toString() + " " + coordinate.toString());

        int sizeX = size.x;
        int sizeY = size.y;
        int coorX = coordinate.x;
        int coorY = coordinate.y;

        int topLeftX = coorX - (sizeX - 1) / 2;
        int topLeftY = coorY - (sizeY - 1) / 2;
        for (int i = topLeftX; i < topLeftX + sizeX; i++) {
            for (int j = topLeftY; j < topLeftY + sizeY; j++) {
                paintPoint(new CoordinateInt(i, j), 1);
            }

        }

    }

    public void clearBuffer() {
        this.buffer = new int[screenSize.x][screenSize.y];
    }

    public void printOut() {
        StringBuffer sb = new StringBuffer('\n');
        for (int j = screenSize.y - 1; j >= 0; j--) {

            for (int i = 0; i < screenSize.x; i++) {
                if (buffer[i][j] == 1) {
                    sb.append('◼');
                } else {
                    sb.append('◻');
                }
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }


}
