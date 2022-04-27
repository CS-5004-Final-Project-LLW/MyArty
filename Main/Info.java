package Main;

import java.util.ArrayList;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * All getters are public and all setter are package accessed.
 */
public class Info {
    // TODO: add a class name `breaker`

    /* Mouse status */
    private static boolean dragging = false;
    private static boolean clicking = false;
    private static boolean pressed = false;
    private static int cursorX = 0;
    private static int cursorY = 0;

    /* Keyboard status */
    private static boolean keyPressed = false;
    private static boolean keyReleased = false;
    private static boolean keyEntered = false;

    /* Debugging for time per frame. Working as a queue */
    private static ArrayList<Integer> sleepTimeRecord = new ArrayList<>();
    private static double rotateDegree = 0;

    /* Images */
    private static Image backgroundImage;
    private static BufferedImage bulletImage;
    private static BufferedImage cannonImage;
    private static BufferedImage cannonBaseImage;
    private static BufferedImage targetImage;
    private static BufferedImage resetButtonImage;
    private static BufferedImage sliderImage;
    private static BufferedImage heartImage;
    private static BufferedImage heartEmptyImage;
    private static BufferedImage pigImage;
    private static BufferedImage pigLeftImage;

    /* Player statics */
    public static final int MAX_LIFE = 5;
    public static final int MIN_LIFE = 0;
    private static final int DEFAULT_LIFE = 5;
    private static final int DEFAULT_SCORE = 0;
    private static int score = Info.DEFAULT_SCORE;
    private static double life = Info.DEFAULT_LIFE;
    private static boolean hitTarget = false;
    private static boolean missShot = false;
    private static boolean restart;

    /* Fire parameters */
    public static int angleValue = 45; // from 0 to 90
    public static int powerValue = 50; // from 0 to 100


    /* Game States */
    static int previousState = -1;
    public final static int TITLE_STATE = 0;
    public final static int PLAY_STATE = 1;
    public final static int PAUSE_STATE = 2;
    public static int gameState = TITLE_STATE;

    private static boolean freezed = false;
    public static final int MAX_WIND = 1;
    public static final int MIN_WIND = -1;
    private static double wind = 0; // from -1 to 1

    private static long counter = 0;

    private static HighestScore highestScore = new HighestScore();


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


    public static BufferedImage getHeartImage() {
        return heartImage;
    }


    public static void setHeartImage(BufferedImage heartImage) {
        Info.heartImage = heartImage;
    }

    public static BufferedImage getPigImage() {
        return pigImage;
    }


    public static void setPigImage(BufferedImage pigImage) {
        Info.pigImage = pigImage;
    }


    public static BufferedImage getPigLeftImage() {
        return pigLeftImage;
    }


    public static void setPigLeftImage(BufferedImage pigLeftImage) {
        Info.pigLeftImage = pigLeftImage;
    }


    public static BufferedImage getHeartEmptyImage() {
        return heartEmptyImage;
    }


    public static void setHeartEmptyImage(BufferedImage heartEmptyImage) {
        Info.heartEmptyImage = heartEmptyImage;
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
        if (score > highestScore.get()) {
            highestScore.set(score);
        }
    }


    public static int getLife() {
        return (int) life;
    }

    public static double getPreciseLife() {
        return life;
    }

    static void setLife(double life) {
        life = Math.max(MIN_LIFE, life);
        life = Math.min(MAX_LIFE, life);
        Info.life = life;
    }

    static void addLife(double life) {
        setLife(getPreciseLife() + life);
    }

    static void removeOneLife() {
        setLife(getPreciseLife() - 1);
    }


    public static double getRotateDegree() {
        return rotateDegree;
    }

    public static void setRotateDegree(double rotateDegree) {
        Info.rotateDegree = rotateDegree;
    }

    public static Image getBackgroundImage() {
        return backgroundImage;
    }


    public static void setBackgroundImage(Image backgroundImage) {
        Info.backgroundImage = backgroundImage;
    }

    /**
     * Set `hit target` flag to true
     */
    public static void Hit() {
        hitTarget = true;
        freezed = true;
    }

    /**
     * Set `miss` flag to true
     */
    public static void miss() {
        missShot = true;
    }



    public static boolean isHitTarget() {
        return hitTarget;
    }


    public static boolean isMissShot() {
        return missShot;
    }


    /*-
     * Set `hit target` flag to true.
     * Package access only
     */
    static void resetHitTarget() {
        hitTarget = false;
    }


    /*-
     * Set `miss` flag to true.
     * Package access only
     */
    static void resetMissShot() {
        missShot = false;
    }

    static void resetScore() {
        setScore(DEFAULT_SCORE);
    }

    static void resetLife() {
        setLife(DEFAULT_LIFE);
    }


    public static boolean isKeyPressed() {
        return keyPressed;
    }


    static void setKeyPressed(boolean keyPressed) {
        Info.keyPressed = keyPressed;
    }


    public static boolean isKeyReleased() {
        return keyReleased;
    }


    static void setKeyReleased(boolean keyReleased) {
        Info.keyReleased = keyReleased;
    }


    public static boolean isKeyEntered() {
        return keyEntered;
    }


    static void setKeyTyped(boolean keyEntered) {
        Info.keyEntered = keyEntered;
    }


    public static boolean isFreezed() {
        return freezed;
    }


    public static void freeze() {
        freezed = true;
    }

    static void resetFreeze() {
        freezed = false;
    }


    public static double getWind() {
        return wind;
    }



    public static long getCounter() {
        return counter;
    }


    public static void addCounter() {
        counter++;
    }


    static void setWind(double wind) {
        wind = Math.max(MIN_WIND, wind);
        wind = Math.min(MAX_WIND, wind);
        Info.wind = wind;
    }


    public static int getHighestScore() {
        return highestScore.get();
    }


    public static boolean isRestart() {
        return restart;
    }


    public static void restart() {
        Info.restart = true;
    }

    static void resetRestart() {
        Info.restart = false;
    }


    /**
     * Reset most of parameters but retain angel, power and cursor.
     */
    static void softReset() {
        freezed = false;
        resetScore();
        resetLife();
        dragging = false;
        clicking = false;
        pressed = false;
        resetHitTarget();
        resetMissShot();
    }

    /**
     * Reset all parameters
     */
    static void hardReset() {
        softReset();
        angleValue = 45;
        powerValue = 50;
        cursorX = 0;
        cursorY = 0;
    }



}


final class Breaker {
    private boolean status = false;

    public boolean get() {
        return status;
    }

    public void set() {
        this.status = true;
    }

    void reset() {
        this.status = false;
    }

}


final class Poster<T> {
    private T status;

    public Poster(T status) {
        this.status = status;
    }

    public T get() {
        return status;
    }

    void set(T status) {
        this.status = status;
    }



}
