package Main;

/**
 * All getters are public and all setter are package accessed.
 */
public class Info {
    private static int angleValue = 45;
    private static int powerValue = 50;

    private static boolean dragging = false;
    private static boolean clicking = false;
    private static int cursorX = 0;
    private static int cursorY = 0;

    public static int getAngleValue() {
        return angleValue;
    }

    static void setAngleValue(int angleValue) {
        Info.angleValue = angleValue;
    }

    public static int getPowerValue() {
        return powerValue;
    }

    static void setPowerValue(int powerValue) {
        Info.powerValue = powerValue;
    }

    public static boolean isDragging() {
        return dragging;
    }

    static void setDragging(boolean dragging) {
        Info.dragging = dragging;
    }

    public static boolean isClicking() {
        return clicking;
    }

    static void setClicking(boolean clicking) {
        Info.clicking = clicking;
    }

    public static int getCursorX() {
        return cursorX;
    }

    static void setCursorX(int cursorX) {
        Info.cursorX = cursorX;
    }

    public static int getCursorY() {
        return cursorY;
    }

    static void setCursorY(int cursorY) {
        Info.cursorY = cursorY;
    }


}