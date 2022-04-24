package Main;

import java.util.ArrayList;
import java.awt.image.BufferedImage;

/**
 * All getters are public and all setter are package accessed.
 */
public class Info {
    /* Mouse status */
    private static boolean dragging = false;
    private static boolean clicking = false;
    private static boolean pressed = false;
    private static int cursorX = 0;
    private static int cursorY = 0;

    /* Debugging for time per frame */
    private static ArrayList<Integer> sleepTimeRecord = new ArrayList<>();
    private static double rotateDegree = 0;

    /* Images */
    private static BufferedImage bulletImage;
    private static BufferedImage cannonImage;
    private static BufferedImage cannonBaseImage;
    private static BufferedImage targetImage;
    private static BufferedImage resetButtonImage;
    private static BufferedImage sliderImage;

    /* Player statics */
    private static final int DEFAULT_LIFE = 5;
    private static final int DEFAULT_SCORE = 0;
    private static int score = Info.DEFAULT_SCORE;
    private static int life = Info.DEFAULT_LIFE;
    private static boolean hitTarget = false;
    private static boolean missShot = false;

    /* Fire parameters */
    public static int angleValue = 45;
    public static int powerValue = 50;
    public static boolean restart;

    /* Game States */
    static int previousState = -1;
    public final static int TITLE_STATE = 0;
    public final static int PLAY_STATE = 1;
    public final static int PAUSE_STATE = 2;
    public static int gameState = TITLE_STATE;



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



    public static int getScore() {
        return score;
    }


    public static void setScore(int score) {
        Info.score = score;
    }


    public static int getLife() {
        return life;
    }


    static void setLife(int life) {
        Info.life = life;
    }

    public static double getRotateDegree() {
        return rotateDegree;
    }

    public static void setRotateDegree(double rotateDegree) {
        Info.rotateDegree = rotateDegree;
    }

    public static void Hit() {
        hitTarget = true;
    }


    public static void miss() {
        missShot = true;
    }


    public static boolean isHitTarget() {
        return hitTarget;
    }


    public static boolean isMissShot() {
        return missShot;
    }

    static void resetHitTarget() {
        hitTarget = false;
    }

    static void resetMissShot() {
        missShot = false;
    }

    static void resetScore() {
        setScore(DEFAULT_SCORE);
    }

    static void resetLife() {
        setLife(DEFAULT_LIFE);
    }

    static void softReset() {
        restart = true;
        resetScore();
        resetLife();
        dragging = false;
        clicking = false;
        pressed = false;
        resetHitTarget();
        resetMissShot();
    }

    static void hardReset() {
        softReset();
        angleValue = 45;
        powerValue = 50;
        cursorX = 0;
        cursorY = 0;
    }



}
