package Main;

import java.util.ArrayList;

/**
 * All getters are public and all setter are package accessed.
 */
public class Info {
    private static boolean dragging = false;
    private static boolean clicking = false;
    private static long lastClickTimeMillis;
    private static boolean pressed = false;
    private static int cursorX = 0;
    private static int cursorY = 0;
    private static ArrayList<Integer> sleepTimeRecord = new ArrayList<>();

    public static int angleValue = 45;
    public static int powerValue = 50;
    public static boolean restart;


    public static boolean isPressed() {
        return pressed;
    }


    static void setPressed(boolean pressed) {
        Info.pressed = pressed;
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
        if (clicking) {
            Info.clicking = true;
            lastClickTimeMillis = System.currentTimeMillis();
        } else {
            if (System.currentTimeMillis() - lastClickTimeMillis > 3 * 1000 / GUI.getFps()) {
                Info.clicking = false;
            }
        }
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

    public static String getDebugInfo() {
        return "Angle value:" + angleValue + ", Power value:" + powerValue + ", Dragging:"
                + dragging + ", Clicking:" + clicking + ", CursorX:" + cursorX + ", CursorY:"
                + cursorY;
    }

    static Integer[] getSleepTimes() {
        Integer[] result = new Integer[sleepTimeRecord.size()];
        sleepTimeRecord.toArray(result);
        return result;
    }

    public static void addSleepTimes(int sleepTime) {
        if (sleepTimeRecord.size() >= GUI.getFps()) {
            sleepTimeRecord.remove(0);
        }
        sleepTimeRecord.add(sleepTime);
    }


}
