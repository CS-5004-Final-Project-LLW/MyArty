package Button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.Info;

public class NewGameButton extends Button {

    public NewGameButton(CoordinateInt coordinate, int width, int height) {
        super(coordinate, width, height);
    }

    @Override
    public boolean update() {
        if (isPressed()) {
            Info.gameState = Info.PLAY_STATE;
        }
        return true;
    }

    @Override
    public void draw(Graphics2D graph) {}


}
