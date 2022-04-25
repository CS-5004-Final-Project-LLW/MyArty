package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JPanel;
import Button.ExitButton;
import Button.NewGameButton;
import Button.RestartButton;
import Coordinate.CoordinateInt;
import Object.Bullet;
import Object.GameObject;
import Slider.PowerSlider;

/**
 * The class for main panel
 */
public class GUI extends JPanel implements Runnable {

  /* NOTE: WIDTH and HEIGHT must be capitalized and static */
  public static int WIDTH;
  public static int HEIGHT;
  private static int fps = 60;

  /* Running controller */
  private Thread workingThread;
  private boolean running;

  // temp image
  private BufferedImage image;

  // thread for printing debugging info
  private Thread debugThread;

  // mouse event capturer
  private MouseCapturer mouseCapturer;


  /**
   * Constuctor for game panel
   * 
   * @param width the width of the main window
   * @param height the height of the main window
   * @param fps frames per second
   */
  public GUI(int width, int height, int fps) {
    /* Initiate main window */
    super();
    GUI.WIDTH = width;
    GUI.HEIGHT = height;
    GUI.fps = fps;
    setPreferredSize(new Dimension(width, height));
    setFocusable(true);
    requestFocus();

    /* Add mouse listener */
    mouseCapturer = new MouseCapturer();
    addMouseMotionListener(mouseCapturer);
    addMouseListener(mouseCapturer);

    /* Load images from files */
    loadAllImage();

    /* Create objects and buttons */
    start();

    /* Used for debugging */
    printDebugInfo();
  }

  /**
   * Load all images from files
   */
  private void loadAllImage() {
    Info.setBackgroundImage(Tools.loadImage("res/background.png"));
    Info.setBulletImage(Tools.loadImage("res/ball.png"));
    Info.setCannonImage(Tools.loadImage("res/cannon2.png"));
    Info.setCannonBaseImage(Tools.loadImage("res/cannon1.png"));
    Info.setTargetImage(Tools.loadImage("res/flyingPig.png"));
    Info.setResetButtonImage(Tools.loadImage("res/bluereset.png"));
    Info.setSliderImage(Tools.loadImage("res/bulletSlide.png"));
  }

  /**
   * Create objects and buttons
   */
  private void start() {
    createObject();
    createButtonInWelcome();
  }

  /**
   * Create objects
   */
  private void createObject() {
    Repo.cannon = Tools.generateCannon();
    Repo.target = Tools.generateTarget();
    Repo.bullets = new HashSet<>();
  }

  /**
   * Create buttons for welcome page
   */
  private void createButtonInWelcome() {
    Repo.newGameButton = new NewGameButton(new CoordinateInt(420, 510), 400, 100);
    Repo.exitButton = new ExitButton(new CoordinateInt(450, 610), 400, 100);
    Repo.restartButton = null;
    Repo.powerSlider = null;
  }

  /**
   * Create buttons for gaming
   */
  private void createButtonInGame() {
    Repo.restartButton = new RestartButton(new CoordinateInt(25, 25), 80, 80);
    Repo.powerSlider = new PowerSlider(new CoordinateInt(50, 150), 100, 20);
    Repo.newGameButton = null;
    Repo.exitButton = null;
  }


  /**
   * Print debug information
   */
  private void printDebugInfo() {
    DebugInfo debugInfo = new DebugInfo();
    debugThread = new Thread(debugInfo);
    debugThread.start();
  }


  /**
   * Return the FPS for the main window
   * 
   * @return int
   */
  public static int getFps() {
    return fps;
  }


  @Override
  public void run() {
    running = true;
    image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    Graphics2D graph = (Graphics2D) image.getGraphics();
    graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    long startTime;

    while (true) {
      if (running) {
        startTime = System.nanoTime();
        if (Info.gameState == Info.TITLE_STATE) {
          gameLoopTitle();
        } else {
          gameLoopPlay(graph, startTime);
        }
      } else {
        Tools.sleepForMills(200);
      }
    }


  }


  /**
   * @param graph
   * @param startTime
   */
  private void gameLoopPlay(Graphics2D graph, long startTime) {
    final long timePerFrame = 1000 / fps;
    long sleepTime;
    long usedTime;
    if (Info.gameState != Info.previousState) {
      Info.previousState = Info.gameState;
      createButtonInGame();
      Info.hardReset();
    }

    // ------ main thread ------ //
    updateAll();
    drawAll(graph);
    showAll();

    Info.setClicking(false);
    checkAll();
    // ---- main thread end ---- //

    usedTime = (System.nanoTime() - startTime) / 1000000;
    // used for debugging
    Info.addSleepTimes((int) usedTime);
    sleepTime = Math.max(0, timePerFrame - usedTime);

    Tools.sleepForMills(sleepTime);
  }

  private void gameLoopTitle() {
    if (Info.gameState != Info.previousState) {
      Info.previousState = Info.gameState;
      createButtonInWelcome();
    }
    updateObject(Repo.exitButton);
    updateObject(Repo.newGameButton);
    drawTitleScreen();

    showAll();

    Tools.sleepForMills(1 / getFps());
  }



  /**
   * @param object
   * @return boolean
   */
  private boolean updateObject(GameObject object) {
    return object != null && object.update();
  }


  private void updateAll() {
    /* Update cannon */
    updateObject(Repo.cannon);

    /* Update bullets */
    ArrayList<Bullet> removedBullet = new ArrayList<>();
    for (Bullet bullet : Repo.bullets) {
      // if update() return false, remove the object itself
      if (!updateObject(bullet)) {
        removedBullet.add(bullet);
        Repo.target = Tools.generateTarget();
      }
    }

    for (Bullet bulletToRemoved : removedBullet) {
      Repo.bullets.remove(bulletToRemoved);
    }

    /* Update target */
    if (!updateObject(Repo.target)) {
      Repo.target = null;
    }

    /* Update buttons */
    updateObject(Repo.fireButton);
    updateObject(Repo.restartButton);

    /* Update sliders */
    updateObject(Repo.powerSlider);
  }

  private void checkAll() {
    /* Deal with Hit */
    if (Info.isHitTarget()) {
      // set hitTarget to false
      Info.resetHitTarget();
      // add score
      Info.setScore(Info.getScore() + 10);
      // add life
      Info.setLife(Math.min(5, Info.getLife() + 1));
      // start a new round
      Info.restart = true;
    }

    /* Restart if hits */
    if (Info.restart) {
      Info.restart = false;
      createObject();
    }

    /* Update life */
    if (Info.isMissShot()) {
      // set missShot to false
      Info.resetMissShot();
      // reduce life
      Info.setLife(Info.getLife() - 1);
      // check remained life
      if (Info.getLife() <= 0) {
        drawGameOver();
      }
    }
  }



  /**
   * @param object
   * @param graph
   */
  private void drawObject(GameObject object, Graphics2D graph) {
    if (object != null) {
      object.draw(graph);
    }
  }


  /**
   * @param graph
   */
  private void drawAll(Graphics2D graph) {
    // Background
    graph.drawImage(Info.getBackgroundImage(), getX(), getY(), WIDTH, HEIGHT, null);

    // Game objects
    drawObject(Repo.cannon, graph);
    drawObject(Repo.target, graph);
    for (Bullet bullet : Repo.bullets) {
      drawObject(bullet, graph);
    }

    drawObject(Repo.fireButton, graph);
    drawObject(Repo.restartButton, graph);
    drawObject(Repo.powerSlider, graph);
  }

  public void showAll() {
    Graphics tempGraph = this.getGraphics();
    tempGraph.drawImage(image, 0, 0, null);
    tempGraph.dispose();
  }


  void drawGameOver() {
    running = false;

    Graphics2D tempGraph = (Graphics2D) this.getGraphics();

    Tools.drawStringWithOutline("Game Over", WIDTH / 2 - 200, HEIGHT / 2 - 100,
        new Font("Serif", Font.BOLD, 70), 10, Color.WHITE, Color.BLACK, tempGraph);

    Tools.drawStringWithOutline("Score: " + Info.getScore(), WIDTH / 2 - 200, HEIGHT / 2 - 50,
        new Font("Arial", Font.BOLD, 40), 15, Color.WHITE, Color.BLACK, tempGraph);

    /* Wait and then restart */
    pauseAndRestart();
  }

  private void pauseAndRestart() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        Tools.sleepForMills(3000);

        /* Set value to default */
        Info.softReset();
        running = true;

        /* Print debug info */
        if (DebugInfo.isRunning()) {
          System.out.println("Set to True");
        }
      }
    }).start();
  }

  public void drawTitleScreen() {
    Graphics2D tempGraph = (Graphics2D) image.createGraphics();
    tempGraph.setColor(new Color(0, 0, 0));
    tempGraph.fillRect(0, 0, 1300, 800);
    // title name
    tempGraph.setFont(tempGraph.getFont().deriveFont(Font.BOLD, 96F));
    String text = "AAA Game";
    int x = 370;
    int y = 230;
    // shadow
    tempGraph.setColor(Color.gray);
    tempGraph.drawString(text, x + 5, y + 5);
    // main color
    tempGraph.setColor(Color.white);
    tempGraph.drawString(text, x, y);
    // menu
    tempGraph.setFont(tempGraph.getFont().deriveFont(Font.BOLD, 48F));
    text = "NEW GAME";
    x = 450;
    y = 550;
    tempGraph.drawString(text, x, y);
    // Repo.newGameButton = new NewGameButton(new CoordinateInt(x, y-10), 300, 100);

    text = "QUIT";
    x = 450;
    y = 650;
    tempGraph.drawString(text, x, y);
    // Repo.exitButton = new ExitButton(new CoordinateInt(x, y-10), 300, 100);

    drawObject(Repo.newGameButton, tempGraph);
    drawObject(Repo.exitButton, tempGraph);

  }

  @Override
  public void addNotify() {
    super.addNotify();
    if (workingThread == null) {
      workingThread = new Thread(this);
      workingThread.start();
    }
  }

}
