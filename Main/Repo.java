package Main;

import TextField.AngleTextField;
import java.util.HashSet;
import Button.Button;
import Object.Bullet;
import Object.Cannon;
import Object.Target;
import Object.Heart;
import Slider.Slider;

/**
 * A class for storing game objects like cannon and target
 */
public class Repo {
    public static Cannon cannon;
    public static Target target;
    public static Heart heart;
    public static Button fireButton;
    public static Button restartButton;
    public static Button newGameButton;
    public static Button exitButton;

    public static Slider powerSlider;
    public static AngleTextField angleTextField;
    public static HashSet<Bullet> bullets = new HashSet<>();


    public static final int MAX_BULLETS = 1;


    /**
     * Check if more bullets can be shot
     * 
     * @return boolean {@code true} if the number of bullets does not reach the limit
     */
    public static boolean isReadyForShot() {
        return Repo.bullets.size() < MAX_BULLETS;
    }

}
// graph.setFont(graph.getFont().deriveFont(Font.BOLD, 25));
// String text2 = "Mouse Click: fire cannon";
// String text3 = "Mouse Cursor: manage the angle";
// String text4 = "Drag slider: control the power";

// // main color
// graph.setColor(Color.white);
// graph.drawString(text, x, y);
// graph.drawString(text2, 400, 400);
// graph.drawString(text3, 400, 440);
// graph.drawString(text4, 400, 440);

