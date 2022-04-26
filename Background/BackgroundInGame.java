package Background;

import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.GUI;
import Main.Info;
import Object.AbstractGameObject;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class BackgroundInGame extends AbstractGameObject {
    private double counter = 0;

    public BackgroundInGame(CoordinateInt coordinate) {
        super(coordinate);
    }

    public int getCounter() {
        return (int) counter;
    }

    public void setCounter(double counter) {
        this.counter = counter % GUI.WIDTH;
    }


    @Override
    public void draw(Graphics2D graph) {
        final int cutX = 250;
        Image backgroundImage = Info.getBackgroundImage();

        // Normal background
        graph.drawImage(backgroundImage, getCounter() / GUI.getFps() % 2, 0, GUI.WIDTH, GUI.HEIGHT,
                null);
        graph.drawImage(backgroundImage, getCounter() / GUI.getFps() % 2 - GUI.WIDTH, 0, GUI.WIDTH,
                GUI.HEIGHT, null);

        /* Cut background */
        BufferedImage buffer = new BufferedImage(GUI.WIDTH, cutX, BufferedImage.TYPE_INT_RGB);
        buffer.createGraphics().drawImage(backgroundImage, 0, 0, GUI.WIDTH, GUI.HEIGHT, null);

        /* Move background */
        graph.drawImage(buffer, getCounter(), 0, buffer.getWidth(), buffer.getHeight(), null);
        graph.drawImage(buffer, getCounter() - GUI.WIDTH, 0, buffer.getWidth(), buffer.getHeight(),
                null);

    }

    @Override
    public boolean update() {
        setCounter(counter + 1);
        return true;
    }

}
