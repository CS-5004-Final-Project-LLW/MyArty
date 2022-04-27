package Main;

import TextField.AngleTextField;
import java.util.HashSet;
import Button.Button;
import Object.Bullet;
import Object.Cannon;
import Object.GameObject;
import Object.Target;
import Object.Heart;
import Object.Pig;
import Slider.Slider;

/**
 * A class for storing game objects like cannon and target
 */
public class Repo {
    public static Cannon cannon;
    public static Target target;
    public static Heart heart;
    public static Pig pig;
    public static Button fireButton;
    public static Button restartButton;
    public static Button newGameButton;
    public static Button exitButton;

    public static Slider powerSlider;
    public static AngleTextField angleTextField;
    public static HashSet<Bullet> bullets = new HashSet<>();
    public static GameObject backgroundInGame;


    public static final int MAX_BULLETS = 1;


    /**
     * Check if more bullets can be shot
     * 
     * @return boolean {@code true} if the number of bullets does not reach the limit
     */
    public static boolean isReadyForShot() {
        return Repo.bullets.size() < MAX_BULLETS && !Info.isFreezed();
    }

}

