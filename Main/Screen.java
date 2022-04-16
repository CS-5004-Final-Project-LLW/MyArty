package Main;

import Object.CoordinateInt;
import Object.GameObject;
import java.util.Arrays;


public class Screen {
    private final CoordinateInt screenSize;
    private int[][] buffer;
    private static final char heartChar = '♥';
    private static final char notHeartChar = '♡';

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

        // paint buffer
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
        // create a new buffer
        this.buffer = new int[screenSize.x][screenSize.y];
    }

    private void printGrass() {
        System.out.println(Color.GREEN_BOLD_BRIGHT);
        String[] grass = new String[screenSize.x];
        Arrays.fill(grass, "▲");
        for (int i = 0; i < screenSize.x; i++) {
            System.out.print(grass[i]);
        }
        System.out.println("");
        System.out.print(Color.RESET);
    }

    public void printOut() {
        StringBuffer sb = new StringBuffer('\n');
        for (int j = screenSize.y - 1; j >= 0; j--) {
            sb.append('\n');
            for (int i = 0; i < screenSize.x; i++) {
                if (buffer[i][j] == 1) {
                    // black dot
                    sb.append('◼');
                } else {
                    // white dot
                    sb.append('◻');
                }
            }
            // wrap
        }

        System.out.print(sb);
        printGrass();
    }

    public static String colorString(String string, Color color) {
        StringBuffer sb = new StringBuffer();
        sb.append(color);
        sb.append(new String(string));
        sb.append(Color.RESET);
        return sb.toString();
    }

    public static String getColorfulString(char[] charArray, Color color) {
        return colorString(new String(charArray), color);
    }

    // life counter
    public void showRemainedLife(int life) {
        if (life < 5 && life > 0) {
            char[] heartArr = new char[5];

            // print '♥'
            for (int i = 0; i < life; i++) {
                heartArr[i] = heartChar;
            }

            // print '♡'
            for (int i = life; i < 5; i++) {
                heartArr[i] = notHeartChar;
            }

            // print colorful characters
            System.out.println("Life: " + getColorfulString(heartArr, Color.RED_BOLD));

        } else if (life <= 0) {
            System.out.println("Game over. Please try again\n");
        }
    }
}
