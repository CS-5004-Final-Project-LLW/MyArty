package Main;

import java.util.ArrayList;
import java.util.Random;
import Object.Bullet;
import Object.Cannon;
import Object.CoordinateInt;
import Object.Target;

public class API {
    private Repository gameRepository;
    private Screen screen;
    private final static int SCREEN_SIZE_X = 40;
    private final static int SCREEN_SIZE_Y = 30;
    // interval between frames
    private final static int SPEED_OF_SHOW_MILLISECOND = 500;
    private String userName = "";

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
        showInit();
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
        Cannon cannon = new Cannon(new CoordinateInt(x, y));
        return cannon;
    }


    /**
     * Create a target at a random position of the right screen
     * 
     * @return Target
     */
    private Target generateTarget() {
        int midX = screen.getScreenSize().x / 2;
        // x should be at the right screen
        int x = midX + new Random().nextInt(midX - 2);
        int y = 1;
        Target target = new Target(new CoordinateInt(x, y));
        return target;
    }

    private void showInit() {
        // clear buffer
        screen.clearBuffer();
        // add cannon
        screen.addObject(gameRepository.getCannon());
        // add target
        screen.addObject(gameRepository.getTarget());
        // print all
        screen.printOut();
    }


    /**
     * @param angleDegree between 0 to 90
     * @param powerPercentage between 1 to 100
     */
    public void shoot(double angleDegree, double powerPercentage) {
        // create an arraylist for storing traces of bullets
        ArrayList<CoordinateInt> traces = gameRepository.getCannon().getShootTrace(angleDegree,
                powerPercentage, gameRepository.getCannon(), screen.getScreenSize());

        // System.out.println(traces.toString());

        for (CoordinateInt coordinate : traces) {
            screen.clearBuffer();
            screen.addObject(gameRepository.getCannon());
            screen.addObject(gameRepository.getTarget());
            screen.addObject(new Bullet(coordinate));
            screen.printOut();
            // sleep for a while
            try {
                Thread.sleep(SPEED_OF_SHOW_MILLISECOND);
            } catch (InterruptedException ex) {
                // catch keyboard interrupt
                Thread.currentThread().interrupt();
            }
        }

        boolean isHit = gameRepository.getCannon().getShootResult(angleDegree, powerPercentage,
                gameRepository.getTarget(), screen.getScreenSize());
        screen.clearBuffer();
        screen.addObject(gameRepository.getCannon());
        // if hit, remove the target
        if (!isHit) {
            screen.addObject(gameRepository.getTarget());
        }
        screen.printOut();
    }


}
