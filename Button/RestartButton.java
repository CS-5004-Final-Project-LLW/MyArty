package Button;

import Coordinate.CoordinateInt;
import Main.Info;
import java.awt.Graphics2D;

public class RestartButton extends Button {

    public RestartButton(CoordinateInt coordinate, int width, int height) {
        super(coordinate, width, height);
    }

    @Override
    public boolean update() {
        if (isPressed()) {
            Info.restart = true;
        }
        return true;
    }

    @Override
    public void draw(Graphics2D graph) {
        graph.drawImage(Info.getResetButtonImage(), coordinate.x, coordinate.y, width, height,
                null);
    }


}
