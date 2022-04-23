package Main;


import Object.Bullet;
import Object.Cannon;
import Object.Target;
import Slider.Slider;
import java.util.HashSet;
import Button.Button;


/**
 * A class for storing game objects like cannon and target
 */
public class Repo {
    public static Cannon cannon;
    public static Target target;
    public static Button fireButton;
    public static Button restartButton;
    public static Slider powerSlider;
    public static HashSet<Bullet> bullets = new HashSet<>();
    


}

