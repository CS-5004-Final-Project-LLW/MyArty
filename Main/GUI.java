package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JPanel;
import Button.ExitButton;
import Button.NewGameButton;
import Button.RestartButton;
import Coordinate.CoordinateInt;
import Object.Bullet;
import Object.GameObject;
import Object.Heart;
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
  private Graphics2D graph;

  // thread for printing debugging info
  private Thread debugThread;

  /* mouse and keyboard event capturer */
  private MouseCapturer mouseCapturer;
  private KeyboardListener keyboardListener;


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

    /* Get a black image */
    image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    graph = Tools.generateGraph(image);

    /* Add mouse listener */
    mouseCapturer = new MouseCapturer();
    addMouseMotionListener(mouseCapturer);
    addMouseListener(mouseCapturer);

    /* Add keyboard listener */
    keyboardListener = new KeyboardListener();
    addKeyListener(keyboardListener);

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
    Info.setBulletImage(Tools.loadImage("res/apple.png"));
    Info.setHeartImage(Tools.loadImage("res/heart1.png"));
    Info.setHeartEmptyImage(Tools.loadImage("res/heart2.png"));
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
    Repo.heart = Tools.generateHeart();
    Repo.bullets = new HashSet<>();
  }


  /**
   * Create buttons for welcome page
   */
  private void createButtonInWelcome() {
    // TODO: change position
    // TODO: extract to `Tools` or `Factory`
    Repo.newGameButton = new NewGameButton(new CoordinateInt(450, 550), 400, 100, "NEW GAME");
    Repo.exitButton = new ExitButton(new CoordinateInt(450, 650), 400, 100, "EXIT");
    Repo.restartButton = null;
    Repo.powerSlider = null;
  }


  /**
   * Create buttons for gaming
   */
  private void createButtonInGame() {
    Repo.restartButton = new RestartButton(new CoordinateInt(25, 25), 100, 100, "RESET");
    Repo.powerSlider = new PowerSlider(new CoordinateInt(25, 170), 150, 30);
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


  /**
   * Method for Runnable
   */
  @Override
  public void run() {
    running = true;

    /* Main loop for all */
    while (true) {
      long startTime = System.nanoTime();
      final long timePerFrame = 1000 / fps;
      long sleepTime, usedTime;

      if (running) {
        if (Info.gameState == Info.TITLE_STATE) {
          /* Title loop */
          gameLoopTitle();
        } else {
          /* Main loop */
          gameLoopPlay();
        }
      }



      /* Wait for next frame */
      usedTime = (System.nanoTime() - startTime) / 1000000;
      // used for debugging
      Info.addSleepTimes((int) usedTime);
      sleepTime = Math.max(0, timePerFrame - usedTime);
      Tools.sleepForMills(sleepTime);
    }
  }


  /**
   * Title loop body.
   * <p>
   * Do not place any {@Code sleep()} method inside.
   */
  private void gameLoopTitle() {
    /* Check transition */
    if (Info.gameState != Info.previousState) {
      Info.previousState = Info.gameState;
      /* Create buttons only for welcome page */
      createButtonInWelcome();
    }

    /* Update buttons and texts */
    updateObject(Repo.exitButton);
    updateObject(Repo.newGameButton);

    // Soft reset mouse and keyboard status
    clearMouseAndKeyboard();

    /* Draw buttons and texts */
    drawTitleScreen();

    /* Refresh screen */
    showAll();
  }


  /**
   * Main game loop body
   * <p>
   * Do not place any {@Code sleep()} method inside.
   */
  private void gameLoopPlay() {
    /* Check transition */
    if (Info.gameState != Info.previousState) {
      Info.previousState = Info.gameState;
      /* Create buttons only for gaming */
      createButtonInGame();
      /* Reset all */
      Info.hardReset();
    }

    // Update objects and buttons
    updateAll();

    // Soft reset mouse and keyboard status
    clearMouseAndKeyboard();

    // Draw objects and buttons
    drawAll();

    // Update life and score
    checkAll();

    // Refresh Screen
    showAll();
  }


  /**
   * Soft reset mouse and keyboard status.
   * <p>
   * Please note most of mouse and keyboard status will be cleared by their opposite.
   * <p>
   * For example, `mouse pressed` resets `mouse released` and vice versa.
   */
  private void clearMouseAndKeyboard() {
    // Clear mouse status
    Info.setClicking(false);
    Info.setKeyTyped(false);
  }


  /**
   * Update object if not null
   * 
   * @param object
   * @return boolean
   */
  private boolean updateObject(GameObject object) {
    return object != null && object.update();
  }
  

  /**
   * Update all objects and buttons exclusing life and score counter
   */
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


  /**
   * Check life, score, hit and miss
   */
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
        /* Draw game over */
        drawGameOver();
        /* Wait and then restart */
        pauseAndRestart();
      }
    }
  }


  /**
   * Draw object if not null
   * 
   * @param object
   * @param graph
   */
  private void drawObject(GameObject object, Graphics2D graph) {
    if (object != null) {
      object.draw(graph);
    }
  }


  /**
   * Draw all objects and buttons
   * 
   */
  private void drawAll() {
    // Draw background
    graph.drawImage(Info.getBackgroundImage(), getX(), getY(), WIDTH, HEIGHT, null);

    // Game objects
    drawObject(Repo.cannon, graph);
    drawObject(Repo.target, graph);
    drawObject(Repo.heart, graph);
    for (Bullet bullet : Repo.bullets) {
      drawObject(bullet, graph);
    }

    drawObject(Repo.fireButton, graph);
    drawObject(Repo.restartButton, graph);
    drawObject(Repo.powerSlider, graph);

    // Score display
    Tools.drawStringWithOutline("Score: " + Info.getScore(), 1050, 25,
    new Font("Arial", Font.BOLD, 40), 15, Color.WHITE, Color.BLACK, graph);

  }


  /**
   * Refresh screen
   * <p>
   * Image will not be updated on the screen until this method called
   */
  public void showAll() {
    // Get real graph
    Graphics tempGraph = this.getGraphics();
    tempGraph.drawImage(image, 0, 0, null);
    tempGraph.dispose();
  }


  /**
   * Draw game-over screen
   */
  void drawGameOver() {
    running = false;
    darkenImage(0.5f);

    final int borderX = WIDTH / 2 - 230;
    final int borderY = HEIGHT / 2 - 120;

    Tools.drawStringWithOutline("Game Over", borderX, borderY, new Font("Serif", Font.BOLD, 70), 10,
        Color.WHITE, Color.BLACK, graph);

    Tools.drawStringWithOutline("Score: " + Info.getScore(), borderX, borderY + 70,
        new Font("Arial", Font.BOLD, 40), 15, Color.WHITE, Color.BLACK, graph);

    Tools.drawStringWithOutline("Press any key to continue", borderX, borderY + 130,
        new Font("Arial", Font.BOLD, 40), 15, Color.WHITE, Color.BLACK, graph);

  }

  /**
   * Darken current image
   * 
   * @param factor
   */
  private void darkenImage(float factor) {
    /* Set rendering hints */
    HashMap<java.awt.RenderingHints.Key, Object> hints = new HashMap<>();
    hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

    /* Rescale image */
    RescaleOp op = new RescaleOp(factor, 0, new RenderingHints(hints));
    this.image = op.filter(image, null);

    /* Set graph */
    this.graph = Tools.generateGraph(this.image);

  }


  /**
   * Wait for seconds and then restart game
   */
  private void pauseAndRestart() {
    // Use a new thread to avoid clogging
    new Thread(new Runnable() {
      @Override
      public void run() {
        final int sleepTimeSecond = 20;
        for (int i = 0; i < sleepTimeSecond * fps; i++) {
          // sleep for 1/fps seconds
          Tools.sleepForMills(1000 / fps);

          // If any key pressed, restart game
          if (Info.isKeyPressed()) {
            break;
          }
        }

        /* Set value to default */
        Info.softReset();
        running = true;

        /* Print debug info */
        if (DebugInfo.isDebugging()) {
          System.out.println("Set to True");
        }
      }
    }).start();
  }


  /**
   * Draw title screen to temp graph
   */
  public void drawTitleScreen() {

    graph.drawImage(Info.getBackgroundImage(), getX(), getY(), WIDTH, HEIGHT, null);
    
    // title name
    graph.setFont(graph.getFont().deriveFont(Font.BOLD, 92F));
    String text = "FLAPPY PIG";
    int x = 380;
    int y = 250;

    // shadow
    graph.setColor(Color.gray);
    graph.drawString(text, x + 5, y + 5);

    // main color
    graph.setColor(Color.white);
    graph.drawString(text, x, y);

    // instructions
    graph.setFont(graph.getFont().deriveFont(Font.ITALIC, 25));
    String text2 = "*            Mouse Click: fire cannon        *";
    String text3 = "*     Move Cursor: manage the angle    *";
    String text4 = "*       Drag Slider: control the power     *";

    // main color
    graph.setColor(Color.white);
    graph.drawString(text2, 400, 400);
    graph.drawString(text3, 400, 430);
    graph.drawString(text4, 400, 460);


    /* Draw buttons */
    drawObject(Repo.newGameButton, graph);
    drawObject(Repo.exitButton, graph);

  }


  /**
   * Method for JPanel
   */
  @Override
  public void addNotify() {
    super.addNotify();
    if (workingThread == null) {
      workingThread = new Thread(this);
      workingThread.start();
    }
  }

}
