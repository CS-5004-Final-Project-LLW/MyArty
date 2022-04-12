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
    private final static int SPEED_OF_SHOW_MILLISECOND = 500;

    public API() {
        screen = new Screen(new CoordinateInt(SCREEN_SIZE_X, SCREEN_SIZE_Y));
        Cannon cannon = generateCannon();
        Target target = generateTarget();
        gameRepository = new Repository(cannon, target);
        showInit();
    }

    private Cannon generateCannon() {
        int midX = screen.getScreenSize().x / 2;
        int x = new Random().nextInt(midX - 2);
        int y = 1;
        Cannon cannon = new Cannon(new CoordinateInt(x, y));
        return cannon;
    }

    private Target generateTarget() {
        int midX = screen.getScreenSize().x / 2;
        int x = midX + 2 + new Random().nextInt(midX - 2);
        int y = 1;
        Target target = new Target(new CoordinateInt(x, y));
        return target;
    }

    private void showInit() {
        screen.clearBuffer();
        screen.addObject(gameRepository.getCannon());
        screen.addObject(gameRepository.getTarget());
        screen.printOut();
    }


    /**
     * @param angleDegree between 0 to 90
     * @param powerPercentage between 1 to 100
     */
    public void shoot(double angleDegree, double powerPercentage) {
        ArrayList<CoordinateInt> traces = gameRepository.getCannon().getShootTrace(angleDegree,
                powerPercentage, gameRepository.getCannon(), screen.getScreenSize());

        // System.out.println(traces.toString());

        for (CoordinateInt coordinate : traces) {
            screen.clearBuffer();
            screen.addObject(gameRepository.getCannon());
            screen.addObject(gameRepository.getTarget());
            screen.addObject(new Bullet(coordinate));
            screen.printOut();
            try {
                Thread.sleep(SPEED_OF_SHOW_MILLISECOND);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        boolean isHit = gameRepository.getCannon().getShootResult(angleDegree, powerPercentage,
                gameRepository.getTarget(), screen.getScreenSize());
        System.out.println(isHit);
        screen.clearBuffer();
        screen.addObject(gameRepository.getCannon());
        if (!isHit) {
            screen.addObject(gameRepository.getTarget());
        }
        screen.printOut();
    }


}
