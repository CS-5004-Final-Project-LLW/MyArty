package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import Button.ExitButton;
import Button.NewGameButton;
import Button.RestartButton;
import Coordinate.CoordinateInt;
import Object.Bullet;
import Object.Cannon;
import Object.GameObject;
import Object.Target;
import Slider.PowerSlider;

public class GUI extends JPanel implements Runnable, MouseListener, MouseMotionListener {

  /* NOTE: WIDTH and HEIGHT must be capitalized and static */
  public static int WIDTH;
  public static int HEIGHT;
  private static int fps = 60;

  private Thread workingThread;
  private boolean running;

  private BufferedImage image;
  // private Graphics2D graph;
  private Image backgroundImage;

  private Thread debugThread;

  public GUI(int WIDTH, int HEIGHT, int fps) {
    super();
    GUI.WIDTH = WIDTH;
    GUI.HEIGHT = HEIGHT;
    GUI.fps = fps;
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    setFocusable(true);
    requestFocus();
    addMouseMotionListener(this);
    addMouseListener(this);

    loadAllImage();
    start();
    printDebugInfo();
  }

  private BufferedImage loadImage(String fileName) {
    File targetImageFile = new File(fileName);
    BufferedImage image = null;
    try {
      image = ImageIO.read(targetImageFile);
    } catch (IOException e) {
      System.out.println(" Image file does not exist.");
      System.exit(-2);
    }
    return image;
  }

  private void loadAllImage() {
    var fileBack = "res/background.png";
    backgroundImage = new ImageIcon(fileBack).getImage();
    Info.setBulletImage(loadImage("res/ball.png"));
    Info.setCannonImage(loadImage("res/cannon2.png"));
    Info.setCannonBaseImage(loadImage("res/cannon1.png"));
    Info.setTargetImage(loadImage("res/flyingPig.png"));
    Info.setResetButtonImage(loadImage("res/bluereset.png"));
    Info.setSliderImage(loadImage("res/bulletSlide.png"));
  }

  private void start() {
    createObject();
    createButtonInWelcome();
  }


  private void createObject() {
    Repo.cannon = generateCannon();
    Repo.target = generateTarget();
    Repo.bullets = new HashSet<>();
  }

  private void createButtonInWelcome() {
    Repo.newGameButton = new NewGameButton(new CoordinateInt(420, 510), 400, 100);
    Repo.exitButton = new ExitButton(new CoordinateInt(450, 610), 400, 100);
    Repo.restartButton = null;
    Repo.powerSlider = null;
  }

  private void createButtonInGame() {
    Repo.restartButton = new RestartButton(new CoordinateInt(25, 25), 80, 80);
    Repo.powerSlider = new PowerSlider(new CoordinateInt(50, 150), 100, 20);
    Repo.newGameButton = null;
    Repo.exitButton = null;
  }


  private void printDebugInfo() {
    DebugInfo debugInfo = new DebugInfo();
    debugThread = new Thread(debugInfo);
    debugThread.start();
  }

  public static int getFps() {
    return fps;
  }


  /**
   * Create a cannon at a random position of the left screen
   *
   * @return Cannon
   */
  private Cannon generateCannon() {
    // x should be at the left screen
    int x = new Random().nextInt(WIDTH * 3 / 10);
    int y = HEIGHT * 4 / 5;
    Cannon cannon = new Cannon(new CoordinateInt(x, y), 150, 50, 80, 60);
    return cannon;
  }

  /**
   * Create a target at a random position of the right screen
   *
   * @return Target
   */
  private Target generateTarget() {
    // x should be at the right screen
    int x = WIDTH * 9 / 10 - new Random().nextInt(WIDTH * 3 / 10);
    int y = HEIGHT * 9 / 10 - new Random().nextInt(HEIGHT * 3 / 10);

    Target target = new Target(new CoordinateInt(x, y), 100, 100);
    return target;
  }


  @Override
  public void run() {
    running = true;
    image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    Graphics2D graph = (Graphics2D) image.getGraphics();
    graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    long startTime, sleepTime, usedTime;
    final long timePerFrame = 1000 / fps;

    while (true) {
      if (running) {
        startTime = System.nanoTime();
        if (Info.gameState == Info.TITLE_STATE) {
          if (Info.gameState != Info.previousState) {
            Info.previousState = Info.gameState;
            createButtonInWelcome();
          }
          updateObject(Repo.exitButton);
          updateObject(Repo.newGameButton);
          drawTitleScreen();

          showAll();

          // TODO: not a nice practice
          try {
            Thread.sleep(1 / getFps());
          } catch (InterruptedException e) {

          }
        } else {
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

          try {
            Thread.sleep(sleepTime);
          } catch (InterruptedException e) {

          }
        }
      } else {
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }


  }

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
        Repo.target = generateTarget();
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



  private void drawObject(GameObject object, Graphics2D graph) {
    if (object != null) {
      object.draw(graph);
    }
  }

  private void drawAll(Graphics2D graph) {
    // Background
    graph.drawImage(backgroundImage, getX(), getY(), WIDTH, HEIGHT, null);

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
        try {
          /* Wait for several seconds */
          Thread.sleep(3000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

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

  @Override
  public void mouseClicked(MouseEvent e) {
    Info.setClicking(true);
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    Info.setDragging(true);
    Info.setCursorX(e.getX());
    Info.setCursorY(e.getY());
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    Info.setCursorX(e.getX());
    Info.setCursorY(e.getY());
  }

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {
    Info.setPressed(true);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    Info.setDragging(false);
    Info.setPressed(false);
  }
}
