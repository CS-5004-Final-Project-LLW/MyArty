package Button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.Info;
import Main.Tools;

public class NewGameButton extends Button {

    public NewGameButton(CoordinateInt coordinate, int width, int height, String text) {
        super(coordinate, width, height, text);
    }

    @Override
    public boolean update() {
        if (isClicked()) {
            Info.gameState = Info.PLAY_STATE;
        }
        return true;
    }

    @Override
    public void draw(Graphics2D graph) {
        super.draw(graph);
    }


}
