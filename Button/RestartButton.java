package Button;

import Coordinate.CoordinateInt;
import Main.Info;

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


}
