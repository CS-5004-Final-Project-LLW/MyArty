package Main;

import java.util.ArrayList;
import java.awt.image.BufferedImage;

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

    private static BufferedImage bulletImage;
    private static BufferedImage cannonImage;
    private static BufferedImage cannonBaseImage;
    private static BufferedImage targetImage;
    private static BufferedImage resetButtonImage;
    private static BufferedImage sliderImage;

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
        // if (clicking) {
        // Info.clicking = true;
        // lastClickTimeMillis = System.currentTimeMillis();
        // } else {
        // if (System.currentTimeMillis() - lastClickTimeMillis > 3 * 1000 / GUI.getFps()) {
        // Info.clicking = false;
        // }
        // }
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


    public static BufferedImage getBulletImage() {
        return bulletImage;
    }


    public static void setBulletImage(BufferedImage bulletImage) {
        Info.bulletImage = bulletImage;
    }


    public static BufferedImage getCannonImage() {
        return cannonImage;
    }


    public static void setCannonImage(BufferedImage cannonImage) {
        Info.cannonImage = cannonImage;
    }

    public static BufferedImage getCannonBaseImage() {
        return cannonBaseImage;
    }


    public static void setCannonBaseImage(BufferedImage cannonBaseImage) {
        Info.cannonBaseImage = cannonBaseImage;
    }


    public static BufferedImage getTargetImage() {
        return targetImage;
    }


    public static void setTargetImage(BufferedImage targetImage) {
        Info.targetImage = targetImage;
    }

    public static BufferedImage getResetButtonImage() {
        return resetButtonImage;
    }


    public static void setResetButtonImage(BufferedImage resetButtonImage) {
        Info.resetButtonImage = resetButtonImage;
    }

    public static BufferedImage getSliderImage() {
        return sliderImage;
    }


    public static void setSliderImage(BufferedImage sliderImage) {
        Info.sliderImage = sliderImage;
    }


    public static int score = GUI.DEFAULT_SCORE;
    public static int life = GUI.DEFAULT_LIFE;

}
