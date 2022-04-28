package Background;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import Coordinate.CoordinateInt;
import Main.GUI;
import Main.Info;
import Object.AbstractGameObject;

public class BackgroundInGame extends AbstractGameObject {
    private double counter = 0;
    private final double MOVEMENT_FACTOR = 2;

    public BackgroundInGame(CoordinateInt coordinate) {
        super(coordinate);
    }

    public int getCounter() {
        return (int) counter;
    }

    public void setCounter(double counter) {
        this.counter = ((counter % GUI.WIDTH) + GUI.WIDTH) % GUI.WIDTH;
    }


    @Override
    public void draw(Graphics2D graph) {
        final int cutX = 250;
        Image backgroundImage = Info.backgroundImage.get();

        // Normal background
        graph.drawImage(backgroundImage, 0, 0, GUI.WIDTH, GUI.HEIGHT, null);

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
        setCounter(counter + Info.getWind() * MOVEMENT_FACTOR * 60 / GUI.getFps());
        return true;
    }

}
