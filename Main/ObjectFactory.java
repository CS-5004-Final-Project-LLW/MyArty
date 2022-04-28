package Main;

import TextField.AngleTextField;
import TextField.WindDisplay;
import java.util.HashSet;
import java.util.Random;
import Background.BackgroundInGame;
import Button.ExitButton;
import Button.NewGameButton;
import Button.RestartButton;
import Coordinate.CoordinateInt;
import Object.Cannon;
import Object.GameObject;
import Object.Heart;
import Object.Pig;
import Object.Target;
import Slider.PowerSlider;

class ObjectFactory {

  static ExitButton generateExitButton() {
    return new ExitButton(new CoordinateInt(580, 590), 400, 60, "EXIT");
  }

  static NewGameButton generateNewGameButton() {
    return new NewGameButton(new CoordinateInt(505, 520), 400, 60, "NEW GAME");
  }

  static PowerSlider generatePowerSlider() {
    return new PowerSlider(new CoordinateInt(25, 170), 150, 40);
  }

  static AngleTextField generateAngleTextField() {
    return new AngleTextField(new CoordinateInt(25, 250), 100, 100);
  }

  static RestartButton generateRestartButton() {
    return new RestartButton(new CoordinateInt(25, 25), 100, 100, "RESET");
  }

  /**
   * Load all images from files
   */
  static void loadAllImage() {
    Info.backgroundImage.set(Tools.loadImage("res/background.png"));
    Info.bulletImage.set(Tools.loadImage("res/apple.png"));
    Info.cannonImage.set(Tools.loadImage("res/cannon2.png"));
    Info.cannonBaseImage.set(Tools.loadImage("res/cannon1.png"));
    Info.targetImage.set(Tools.loadImage("res/flyingPig.png"));
    Info.resetButtonImage.set(Tools.loadImage("res/bluereset.png"));
    Info.sliderImage.set(Tools.loadImage("res/bulletSlide.png"));
    Info.sliderBarImage.set(Tools.loadImage("res/colorBar.png"));

    Info.heartImage.set(Tools.loadImage("res/heart1.png"));
    Info.heartEmptyImage.set(Tools.loadImage("res/heart2.png"));
    Info.pigImage.set(Tools.loadImage("res/pig1.png"));
    Info.pigLeftImage.set(Tools.loadImage("res/pig1_l.png"));

  }

  /**
   * Create objects
   */
  static void createObject() {
    Repo.cannon = ObjectFactory.generateCannon();
    Repo.target = ObjectFactory.generateTarget();
    Repo.bullets = new HashSet<>();
  }

  /**
   * Create buttons for welcome page
   */
  static void createButtonInWelcome() {
    Repo.newGameButton = generateNewGameButton();
    Repo.exitButton = generateExitButton();
    Repo.pig = new Pig(new CoordinateInt(100, 100), 200, 170);
    Repo.restartButton = null;
    Repo.powerSlider = null;
  }

  /**
   * Create buttons for gaming
   */
  static void createButtonInGame() {
    Repo.restartButton = generateRestartButton();
    Repo.powerSlider = generatePowerSlider();
    Repo.newGameButton = null;
    Repo.exitButton = null;
    Repo.heart = generateHeart();
    Repo.angleTextField = generateAngleTextField();
    Repo.backgroundInGame = generateBackgroundInGame();
    Repo.windDisplay = generateWindDisplay();
  }

  private static GameObject generateBackgroundInGame() {
    return new BackgroundInGame(new CoordinateInt(0, 0));
  }

  /**
   * Create a Heart at a random position of the right screen
   *
   * @return Heart
   */
  static Heart generateHeart() {
    return new Heart(new CoordinateInt(GUI.WIDTH / 2 - 20, 35), 50, 50, 60);
  }

  /**
   * Create a target at a random position of the right screen
   *
   * @return Target
   */
  static Target generateTarget() {
    // x should be at the right screen
    int x = GUI.WIDTH * 9 / 10 - new Random().nextInt(GUI.WIDTH * 3 / 10);
    int y = GUI.HEIGHT * 1 / 4 + new Random().nextInt(GUI.HEIGHT / 2);

    Target target = new Target(new CoordinateInt(x, y), 100, 100);
    return target;
  }

  /**
   * Create a cannon at a random position of the left screen
   *
   * @return Cannon
   */
  static Cannon generateCannon() {
    // x should be at the left screen
    int x = new Random().nextInt(GUI.WIDTH * 3 / 10);
    int y = GUI.HEIGHT * 3 / 4;
    Cannon cannon = new Cannon(new CoordinateInt(x, y), 150, 50, 80, 60);
    return cannon;
  }

  static double generateRandomWind() {
    return new Random().nextDouble(Info.MIN_WIND, Info.MAX_WIND);
  }

  static WindDisplay generateWindDisplay() {
    return new WindDisplay(new CoordinateInt(400, 20));
  }
}
