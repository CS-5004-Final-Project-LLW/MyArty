package Main;

import javax.swing.JPanel;
import Coordinate.CoordinateInt;
import Object.Cannon;
import Object.Target;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;

public class GUI extends JPanel implements Runnable, MouseListener, MouseMotionListener {

    /* NOTE: WIDTH and HEIGHT must be capitalized and static */
    public static int WIDTH;
    public static int HEIGHT;
    private int fps = 60;

    private Thread workingThread;
    private boolean running;

    private BufferedImage image;
    private Graphics2D graph;

    /* TODO: make a new class called InfoPanel */
    private static int angleValue = 0;
    private static int powerValue = 0;

    private static boolean dragging = false;
    private static boolean clicking = false;
    private static int cursorX = 0;
    private static int cursorY = 0;

    private Repository repo;

    public static boolean isDragging() {
        return dragging;
    }


    public static boolean isClicking() {
        return clicking;
    }


    public static int getCursorX() {
        return cursorX;
    }


    public static int getCursorY() {
        return cursorY;
    }


    public static void setCursorY(int cursorY) {
        GUI.cursorY = cursorY;
    }


    public int getAngleValue() {
        return angleValue;
    }


    void setAngleValue(int angleValue) {
        GUI.angleValue = angleValue;
    }


    public int getPowerValue() {
        return powerValue;
    }


    void setPowerValue(int powerValue) {
        GUI.powerValue = powerValue;
    }


    public GUI(int WIDTH, int HEIGHT, int fps) {
        super();
        GUI.WIDTH = WIDTH;
        GUI.HEIGHT = HEIGHT;
        this.fps = fps;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        addMouseMotionListener(this);
        addMouseListener(this);

        Cannon cannon = generateCannon();
        Target target = generateTarget();

        repo = new Repository(cannon, target);
    }

    /**
     * Create a cannon at a random position of the left screen
     * 
     * @return Cannon
     */
    private Cannon generateCannon() {
        int midX = WIDTH / 2;
        // x should be at the left screen
        int x = 1 + new Random().nextInt(midX - 3);
        int y = 1;
        Cannon cannon = new Cannon(new CoordinateInt(x, y), new CoordinateInt(WIDTH, HEIGHT));
        return cannon;
    }


    /**
     * Create a target at a random position of the right screen
     * 
     * @return Target
     */
    private Target generateTarget() {
        int midX = WIDTH / 2;
        int midY = HEIGHT / 2;
        // x should be at the right screen
        int x = midX + 3 + new Random().nextInt(midX - 4);
        int y = 1 + new Random().nextInt(midY + 1);;
        Target target = new Target(new CoordinateInt(x, y), new CoordinateInt(WIDTH, HEIGHT));
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
            // ---- main thread end ---- //

            usedTime = (System.nanoTime() - startTime) / 1000000;
            sleepTime = Math.max(0, timePerFrame - usedTime);

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {

            }
        }

    }

    private void updateAll() {
        repo.getCannon().update();
        repo.getTarget().update();
    }


    private void drawAll() {
        // Background and grass
        graph.setColor(new Color(197, 234, 243));
        graph.fillRect(0, 0, WIDTH, HEIGHT);

        // Game objects
        repo.getCannon().draw();
        repo.getTarget().draw();


    }

    public void showAll() {
        Graphics tempGraph = this.getGraphics();
        tempGraph.drawImage(image, 0, 0, null);
        tempGraph.dispose();
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
        clicking = true;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dragging = true;
        cursorX = e.getX();
        cursorY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        dragging = false;
        clicking = false;
        cursorX = e.getX();
        cursorY = e.getY();
    }


    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

}
