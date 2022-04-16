package Main;

import java.util.ArrayList;
import java.util.Random;
import Object.Bullet;
import Object.Cannon;
import Object.CoordinateInt;
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
    private int life;

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
        showInit();
        // life counter
        life = 5;
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
        int x = midX + 3 + new Random().nextInt(midX - 4);
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

    // life counter
    public void heartArr(int life) {
        char[] heartArr = {'♥', '♥', '♥', '♥', '♥'};
        if (life < 5 && life > 0) {
            for (int i = 4; i >= life; i--) {
                heartArr[i] = '♡';
            }
            System.out.print("Life:");
            System.out.print(Color.RED_BOLD);
            System.out.printf(new String(heartArr) + "\n");
            System.out.print(Color.RESET);
        } else if (life <= 0) {
            System.out.println("Game over. Please try again\n");

        }
    }

    /**
     * @param angleDegree between 0 to 90
     * @param powerPercentage between 1 to 100
     */
    public void shoot(double angleDegree, double powerPercentage) {
        // create an arraylist for storing traces of bullets
        ArrayList<CoordinateInt> traces = gameRepository.getCannon().getShootTrace(angleDegree,
                powerPercentage, gameRepository.getCannon(), screen.getScreenSize());

        /* Display traces of bullets */
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

        /* Display the last frame */
        boolean isHit = gameRepository.getCannon().getShootResult(angleDegree, powerPercentage,
                gameRepository.getTarget(), screen.getScreenSize());
        screen.clearBuffer();
        screen.addObject(gameRepository.getCannon());

        /* if hit, remove the target */
        if (!isHit) {
            GameObject target = gameRepository.getTarget();

            /*-
             * Randomly remove the x position of target
             * dx from -2 to 2
             */
            int x = target.getX() + new Random().nextInt(-2, 3);

            /* Avoid the target moving out of the screen or designated position */
            int upperBoundX = screen.getScreenSize().x - 2;
            if (x > upperBoundX) {
                x = upperBoundX;
            }

            int lowerBoundX = screen.getScreenSize().x / 2 + 3;
            if (x < lowerBoundX) {
                x = lowerBoundX;
            }

            /*-
             * Randomly remove the y position of target
             * dy from -2 to 2
             */
            int y = target.getY() + new Random().nextInt(-2, 3);

            /* Avoid the target moving out of the screen or designated position */
            int upperBoundY = 5;
            if (y > upperBoundY) {
                y = upperBoundY;
            }

            int lowerBoundY = 1;
            if (y < lowerBoundY) {
                y = lowerBoundY;
            }

            /* Set x, y postion */
            target.setCoordinate(new CoordinateInt(x, y));

            // add target
            screen.addObject(target);
            life--;
        }
        // print out all from buffer
        screen.printOut();
        // display remained lives
        heartArr(life);
    }
}
