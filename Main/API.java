package Main;

import java.util.ArrayList;
import java.util.Random;
import Coordinate.CoordinateInt;
import Display.Screen;
import Object.Bullet;
import Object.Cannon;
import Object.GameObject;
import Object.Target;

public class API {
    private Repository gameRepository;
    private Screen screen;
    private final static int SCREEN_SIZE_X = 40;
    private final static int SCREEN_SIZE_Y = 30;
    // interval between frames
    private final static int SPEED_OF_SHOW_MILLISECOND = 500;
    private String userName = "";
    private int life = 5;


    public int getLife() {
        return life;
    }

    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public API(String userName) {
        this.userName = userName;
        // create a screen
        screen = new Screen(new CoordinateInt(SCREEN_SIZE_X, SCREEN_SIZE_Y));
        // create a cannon
        Cannon cannon = generateCannon();
        // create a target
        Target target = generateTarget();
        // place cannon and target into repository
        gameRepository = new Repository(cannon, target);
        // show welcome
        showWelcomeInformation();
    }


    /**
     * Create a cannon at a random position of the left screen
     * 
     * @return Cannon
     */
    private Cannon generateCannon() {
        int midX = screen.getScreenSize().x / 2;
        // x should be at the left screen
        int x = 1 + new Random().nextInt(midX - 3);
        int y = 1;
        Cannon cannon = new Cannon(new CoordinateInt(x, y), screen.getScreenSize());
        return cannon;
    }


    /**
     * Create a target at a random position of the right screen
     * 
     * @return Target
     */
    private Target generateTarget() {
        int midX = screen.getScreenSize().x / 2;
        int midY = screen.getScreenSize().y / 2;
        // x should be at the right screen
        int x = midX + 3 + new Random().nextInt(midX - 4);
        int y = 1 + new Random().nextInt(midY + 1);;
        Target target = new Target(new CoordinateInt(x, y), screen.getScreenSize());
        return target;
    }

    private void showWelcomeInformation() {
        // clear buffer
        screen.clearBuffer();
        // add cannon
        screen.addObject(gameRepository.getCannon(),/*type=*/3 );
        // add target
        screen.addObject(gameRepository.getTarget(),/*type=*/4);
        // print all
        screen.printOut();
        // display remained lives
        screen.showRemainedLife(life);
    }



    /**
     * @param angleDegree between 0 to 90
     * @param powerPercentage between 1 to 100
     */
    public void shoot(double angleDegree, double powerPercentage, String isSkip) {
        // create an arraylist for storing traces of bullets
        ArrayList<CoordinateInt> traces = gameRepository.getCannon().getShootTrace(angleDegree,
                powerPercentage, gameRepository.getCannon(), screen.getScreenSize());

        /* Display traces of bullets */
        // check skip
        if (!isSkip.equals("y") && !isSkip.equals("Y")) {
            for (int i = 0; i < traces.size(); i++) {
                screen.clearBuffer();
                // display traces of shadow
                if (i > 3) {
                    screen.addObject(new Bullet(traces.get(i - 1)),/*type=*/1);
                    screen.addObject(new Bullet(traces.get(i - 2)),/*type=*/1);
                    screen.addObject(new Bullet(traces.get(i - 3)),/*type=*/1);
                }
                screen.addObject(gameRepository.getCannon(),/*type=*/3);
                screen.addObject(gameRepository.getTarget(),/*type=*/4);
                screen.addObject(new Bullet(traces.get(i)),/*type=*/2);
                screen.printOut();

                // sleep for a while

                try {
                    Thread.sleep(SPEED_OF_SHOW_MILLISECOND);
                } catch (InterruptedException ex) {
                    // catch keyboard interrupt
                    Thread.currentThread().interrupt();
                }

            }
        }

        /* Display the last frame */
        boolean isHit = gameRepository.getCannon().getShootResult(angleDegree, powerPercentage,
                gameRepository.getTarget(), screen.getScreenSize());
        screen.clearBuffer();
        screen.addObject(gameRepository.getCannon(),/*type=*/3);

        /* if hit, remove the target */
        if (!isHit) {
            GameObject target = gameRepository.getTarget();

            // Randomly remove the x position of target with dx from -2 to 2
            int x = target.getX() + new Random().nextInt(-2, 3);

            // Randomly remove the y position of target with dy from -2 to 2
            int y = target.getY() + new Random().nextInt(-2, 3);

            /* Set x, y postion */
            target.setCoordinate(new CoordinateInt(x, y));

            // add target
            screen.addObject(target,4);

            // lives minus one because of failure
            life--;
        }
        // print out all from buffer
        screen.printOut();

        // display remained lives
        screen.showRemainedLife(life);
    }
}
