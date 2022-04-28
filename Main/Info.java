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
    public static final Poster<Boolean> dragging = new Poster<>(false);
    public static final Poster<Boolean> clicking = new Poster<>(false);
    public static final Poster<Boolean> pressed = new Poster<>(false);
    public static final Poster<Integer> cursorX = new Poster<Integer>(0);
    public static final Poster<Integer> cursorY = new Poster<Integer>(0);

    /* Keyboard status */
    public static final Poster<Boolean> keyPressed = new Poster<>(false);
    public static final Poster<Boolean> keyReleased = new Poster<>(false);
    public static final Poster<Boolean> keyEntered = new Poster<>(false);

    /* Debugging for time per frame. Working as a queue */
    private static ArrayList<Integer> sleepTimeRecord = new ArrayList<>();
    private static double rotateDegree = 0;

    /* Images */
    public static final Poster<BufferedImage> backgroundImage = new Poster<>(null);
    public static final Poster<BufferedImage> bulletImage = new Poster<>(null);
    public static final Poster<BufferedImage> cannonImage = new Poster<>(null);
    public static final Poster<BufferedImage> cannonBaseImage = new Poster<>(null);
    public static final Poster<BufferedImage> targetImage = new Poster<>(null);
    public static final Poster<BufferedImage> resetButtonImage = new Poster<>(null);
    public static final Poster<BufferedImage> sliderImage = new Poster<>(null);
    public static final Poster<BufferedImage> heartImage = new Poster<>(null);
    public static final Poster<BufferedImage> heartEmptyImage = new Poster<>(null);
    public static final Poster<BufferedImage> pigImage = new Poster<>(null);
    public static final Poster<BufferedImage> pigLeftImage = new Poster<>(null);

    /* Player statics */
    public static final int MAX_LIFE = 5;
    public static final int MIN_LIFE = 0;
    private static final int DEFAULT_LIFE = 5;
    private static final int DEFAULT_SCORE = 0;

    private static int score = DEFAULT_SCORE;
    private static double life = DEFAULT_LIFE;

    public static final Breaker hitTarget = new Breaker();
    public static final Breaker missShot = new Breaker();
    public static final Breaker restart = new Breaker();
    public static final Breaker freezed = new Breaker();

    /* Fire parameters */
    public static int angleValue = 45; // from 0 to 90
    public static int powerValue = 50; // from 0 to 100


    /* Game States */
    public final static int TITLE_STATE = 0;
    public final static int PLAY_STATE = 1;
    public final static int PAUSE_STATE = 2;

    static int previousState = -1;
    public static int gameState = TITLE_STATE;

    public static final int MAX_WIND = 1;
    public static final int MIN_WIND = -1;

    private static double wind = 0; // from -1 to 1

    private static long counter = 0;

    private static final HighestScore highestScore = new HighestScore();

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


    /**
     * Set `hit target` flag to true
     */
    public static void Hit() {
        hitTarget.set();
        freezed.set();
    }

    /**
     * Set `miss` flag to true
     */
    public static void miss() {
        missShot.set();
    }

    static void resetScore() {
        setScore(DEFAULT_SCORE);
    }

    static void resetLife() {
        setLife(DEFAULT_LIFE);
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



    /**
     * Reset most of parameters but retain angel, power and cursor.
     */
    static void softReset() {
        freezed.reset();
        resetScore();
        resetLife();
        dragging.set(false);
        clicking.set(false);
        pressed.set(false);
        hitTarget.reset();
        missShot.reset();
    }

    /**
     * Reset all parameters
     */
    static void hardReset() {
        softReset();
        angleValue = 45;
        powerValue = 50;
        cursorX.set(0);
        cursorY.set(0);
    }

}
