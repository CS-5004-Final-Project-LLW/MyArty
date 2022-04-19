package Display;

// import Object.Coordinate;
import Object.GameObject;

import Coordinate.CoordinateInt;

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

    // type defines an game object type to distinguish the printed char
    // 1 trace of bullet
    // 2 bullet current
    // 3 cannon
    // 4 target
    public void addObject(GameObject gameObject, int type) {
        CoordinateInt size = gameObject.getSize();
        CoordinateInt coordinate = gameObject.getCoordinate();

        int sizeX = size.x;
        int sizeY = size.y;
        int coorX = coordinate.x;
        int coorY = coordinate.y;

        int topLeftX = coorX - (sizeX - 1) / 2;
        int topLeftY = coorY - (sizeY - 1) / 2;
        for (int i = topLeftX; i < topLeftX + sizeX; i++) {
            for (int j = topLeftY; j < topLeftY + sizeY; j++) {
                paintPoint(new CoordinateInt(i, j), type);
            }
        }
    }

    public void clearBuffer() {
        // create a new buffer
        this.buffer = new int[screenSize.x][screenSize.y];
    }

    private void printGrass() {
        StringBuffer sb = new StringBuffer();

        sb.append(Color.GREEN_BOLD_BRIGHT);
        for (int i = 0; i < screenSize.x; i++) {
            sb.append("▲");
        }
        sb.append('\n');
        sb.append(Color.RESET);

        System.out.println(sb.toString());
    }

    public void printOut() {
        StringBuffer sb = new StringBuffer('\n');
        for (int j = screenSize.y - 1; j >= 0; j--) {
            // wrap
            sb.append('\n');
            for (int i = 0; i < screenSize.x; i++) {
                // 1 trace of bullet
                // 2 bullet current
                // 3 cannon
                // 4 target
                // 5 explosion
                switch (buffer[i][j]) {
                    case 1:
                        // trace bullet
                        sb.append('◼');
                        break;
                    case 2:
                        // bullet current
                        sb.append(Screen.colorString('▶', Color.RED_BRIGHT));
                        break;
                    case 3:
                        sb.append(Screen.colorString('✪', Color.BLUE_BRIGHT));
                        break;
                    case 4:
                        sb.append(Screen.colorString('⬢', Color.YELLOW_BRIGHT));
                        break;
                    case 5:
                        sb.append(Screen.colorString('·', Color.RED_BRIGHT));
                    default:
                        sb.append('◻');
                }
            }
        }
        System.out.println(sb.toString());

        printGrass();
    }

    public static String colorString(String string, Color color) {
        StringBuffer sb = new StringBuffer();
        sb.append(color);
        sb.append(new String(string));
        sb.append(Color.RESET);
        return sb.toString();
    }

    public static String colorString(char[] charArray, Color color) {
        return colorString(new String(charArray), color);
    }

    public static String colorString(char cha, Color color) {
        return colorString(String.valueOf(cha), color);
    }

    // life counter
    public void showRemainedLife(int life) {
        if (life > 0) {
            StringBuffer sb = new StringBuffer();

            // print '♥'
            for (int i = 0; i < life; i++) {
                sb.append(heartChar);
            }

            // print '♡'
            for (int i = life; i < 5; i++) {
                sb.append(notHeartChar);
            }

            // print colorful characters
            System.out.println(
                    (life > 1 ? "Lives:" : "Life:") + colorString(sb.toString(), Color.RED_BOLD));

        } else {
            System.out.println("Game over. ");
        }
    }
   
}
