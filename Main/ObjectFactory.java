package Main;

import TextField.AngleTextField;
import java.util.HashSet;
import java.util.Random;
import Button.ExitButton;
import Button.NewGameButton;
import Button.RestartButton;
import Coordinate.CoordinateInt;
import Object.Heart;
import Slider.PowerSlider;

class ObjectFactory {

  static ExitButton generateExitButton() {
    return new ExitButton(new CoordinateInt(450, 650), 400, 100, "EXIT");
  }

  static NewGameButton generateNewGameButton() {
    return new NewGameButton(new CoordinateInt(450, 550), 400, 100, "NEW GAME");
  }

  static PowerSlider generatePowerSlider() {
    return new PowerSlider(new CoordinateInt(25, 170), 150, 30);
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
    Info.setBackgroundImage(Tools.loadImage("res/background.png"));
    Info.setBulletImage(Tools.loadImage("res/apple.png"));
    Info.setCannonImage(Tools.loadImage("res/cannon2.png"));
    Info.setCannonBaseImage(Tools.loadImage("res/cannon1.png"));
    Info.setTargetImage(Tools.loadImage("res/flyingPig.png"));
    Info.setResetButtonImage(Tools.loadImage("res/bluereset.png"));
    Info.setSliderImage(Tools.loadImage("res/bulletSlide.png"));
    Info.setHeartImage(Tools.loadImage("res/heart1.png"));
    Info.setHeartEmptyImage(Tools.loadImage("res/heart2.png"));
  }

  /**
   * Create objects
   */
  static void createObject() {
    Repo.cannon = Tools.generateCannon();
    Repo.target = Tools.generateTarget();
    Repo.bullets = new HashSet<>();
  }

  /**
   * Create buttons for welcome page
   */
  static void createButtonInWelcome() {
    Repo.newGameButton = generateNewGameButton();
    Repo.exitButton = generateExitButton();
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
  }

  /**
   * Create a Heart at a random position of the right screen
   *
   * @return Heart
   */
  static Heart generateHeart() {
    // x should be at the right screen
    int x = GUI.WIDTH * 9 / 10 - new Random().nextInt(GUI.WIDTH * 3 / 10);
    int y = new Random().nextInt(GUI.HEIGHT * 3 / 4);

    return new Heart(new CoordinateInt(x, y), 100, 100);

  }
}
