package Main;

import javax.swing.JPanel;
import Coordinate.CoordinateInt;
import Object.Bullet;
import Object.Button;
import Object.Cannon;
import Object.GameObject;
import Object.Target;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GUI extends JPanel implements Runnable, MouseListener, MouseMotionListener {

    /* NOTE: WIDTH and HEIGHT must be capitalized and static */
    public static int WIDTH;
    public static int HEIGHT;
    private static int fps = 60;

    private Thread workingThread;
    private boolean running;
    private Thread debugThread;

    private BufferedImage image;
    private Graphics2D graph;



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

        Repo.cannon = generateCannon();
        Repo.target = generateTarget();
        Repo.fireButton = generateButton();

        printDebugInfo();
    }

    private void printDebugInfo() {
        DebugInfo debugInfo = new DebugInfo();
        debugThread = new Thread(debugInfo);
        debugThread.start();
    }

    public static int getFps() {
        return fps;
    }

    private Button generateButton() {
        return new Button(new CoordinateInt(300, 300), 100, 100);
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
        Cannon cannon = new Cannon(new CoordinateInt(x, y));
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
        int x = WIDTH - new Random().nextInt(midX * 3 / 5);
        int y = HEIGHT - new Random().nextInt(midY * 3 / 5);;
        Target target = new Target(new CoordinateInt(x, y));
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
            clearMouseStatus();
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
        Repo.cannon.update();
        Repo.target.update();
        ArrayList<Bullet> removedBullet = new ArrayList<>();
        for (Bullet bullet : Repo.bullets) {
            // if update() return false, remove the object itself
            if (!bullet.update()) {
                removedBullet.add(bullet);
            }
        }
        for (Bullet bulletToRemoved : removedBullet) {
            Repo.bullets.remove(bulletToRemoved);
        }
        Repo.fireButton.update();
    }


    private void drawAll() {
        // Background
        graph.setColor(new Color(197, 234, 243));
        graph.fillRect(0, 0, WIDTH, HEIGHT);

        // Game objects
        Repo.cannon.draw(graph);
        Repo.target.draw(graph);
        for (Bullet bullet : Repo.bullets) {
            bullet.draw(graph);
        }
        Repo.fireButton.draw(graph);

    }

    public void showAll() {
        Graphics tempGraph = this.getGraphics();
        tempGraph.drawImage(image, 0, 0, null);
        tempGraph.dispose();
    }

    private void clearMouseStatus() {
        Info.setClicking(false);
        Info.setDragging(false);
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
        // Info.setClicking(false);
        // Info.setDragging(false);
        Info.setCursorX(e.getX());
        Info.setCursorY(e.getY());
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
