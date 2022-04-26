package Background;

import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.GUI;
import Main.Info;
import Object.AbstractGameObject;

public class BackgroundInGame extends AbstractGameObject {

    public BackgroundInGame(CoordinateInt coordinate) {
        super(coordinate);
    }


    @Override
    public void draw(Graphics2D graph) {
        graph.drawImage(Info.getBackgroundImage(), 0, 0, GUI.WIDTH, GUI.HEIGHT, null);

    }

    @Override
    public boolean update() {
        return true;
    }

}
