package Main;


import Object.Bullet;
import Object.Button;
import Object.Cannon;
import Object.Target;
import java.util.HashSet;


/**
 * A class for storing game objects like cannon and target
 */
public class Repo {
    public static Cannon cannon;
    public static Target target;
    public static Button fireButton;
    public static HashSet<Bullet> bullets = new HashSet<>();


}
