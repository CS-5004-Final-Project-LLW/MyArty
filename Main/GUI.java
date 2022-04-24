package Main;

import javax.swing.JPanel;
import Button.RestartButton;
import Coordinate.CoordinateInt;
import Object.*;
import Slider.PowerSlider;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;

import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class GUI extends JPanel implements Runnable, MouseListener, MouseMotionListener {

  /* NOTE: WIDTH and HEIGHT must be capitalized and static */
  public static int WIDTH;
  public static int HEIGHT;
  private static int fps = 60;

  public static int life = 5;
  public static int score = 0;

  private Thread workingThread;
  private boolean running;
  private boolean isHit = false;

  private BufferedImage image;
  private Graphics2D graph;
  private Image background_image;

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
    background_image = new ImageIcon(fileBack).getImage();
    Info.setBulletImage(loadImage("res/bullet.png"));
    Info.setCannonImage(loadImage("res/cannon.jpeg"));
    Info.setTargetImage(loadImage("res/target.png"));
  }

  private void start() {
    createObject();
    // Repo.fireButton = new FireButton(new CoordinateInt(300, 300), 100, 100);
    Repo.restartButton = new RestartButton(new CoordinateInt(50, 50), 100, 100);
    Repo.powerSlider = new PowerSlider(new CoordinateInt(50, 150), 100, 20);
  }

  private void createObject() {
    Repo.cannon = generateCannon();
    Repo.target = generateTarget();
    Repo.bullets = new HashSet<>();
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
    Cannon cannon = new Cannon(new CoordinateInt(x, y), 150, 150);
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
    graph = (Graphics2D) image.getGraphics();
    graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
    long startTime, sleepTime, usedTime;
    final long timePerFrame = 1000 / fps;

    while (running) {
      startTime = System.nanoTime();

      // ------ main thread ------ //
      updateAll();
      drawAll();
      showAll();
      // clearMouseStatus();
      Info.setClicking(false);
      // ---- main thread end ---- //

      usedTime = (System.nanoTime() - startTime) / 1000000;
      sleepTime = timePerFrame - usedTime;
      Info.addSleepTimes((int) usedTime);
      sleepTime = Math.max(0, timePerFrame - usedTime);

      try {
        Thread.sleep(sleepTime);
      } catch (InterruptedException e) {

      }
      if (life == 0) {
        running = false;
        drawGameOver();
      }
    }
      
  }

  private boolean updateObject(GameObject object) {
    return object != null && object.update();
  }

  private void updateAll() {
    /* Restart */
    if (Info.restart) {
      Info.restart = false;
      createObject();
    }

    /* Update cannon */
    updateObject(Repo.cannon);

    /* Update bullets */
    ArrayList<Bullet> removedBullet = new ArrayList<>();
    for (Bullet bullet : Repo.bullets) {
      // if update() return false, remove the object itself
      if (!updateObject(bullet)) {
        removedBullet.add(bullet);
        scorelifeSetter(isHit);
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


  public void scorelifeSetter(boolean isHit){
    if(isHit){
        if (life<5){
        life++;
        }
        score+=10;
    }else{
      life--;
    }
  }
  
  private void drawObject(GameObject object, Graphics2D graph) {
    if (object != null) {
      object.draw(graph);
    }
  }


  private void drawAll() {
    // Background
    graph.drawImage(background_image,getX(),getY(),WIDTH, HEIGHT,null);


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

  private void drawGameOver() {
    Graphics tempGraph = this.getGraphics();
    tempGraph.setFont(new Font("Serif", Font.BOLD, 70));
    tempGraph.drawString("Game Over", 320, 90);

    tempGraph.setFont(new Font("Arial", Font.BOLD, 40));
    tempGraph.drawString("Score: " + score, 410, 150);
  }

  // private void drawGamePaused(Graphics2D graph) {
  //   graph.setFont(new Font("Serif", Font.BOLD, 60));
  //   graph.drawString("Game Paused", 310, 150);
  // }

  private void clearMouseStatus() {
    Info.setClicking(false);
    Info.setDragging(false);
    Info.setPressed(false);
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