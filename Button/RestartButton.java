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
//        super.draw(graph);
//        graph.setColor(Color.white);
//        Font f = new Font("Calibri", Font.BOLD, 25);
//        graph.setFont(f);
//        graph.drawString("reset",coordinate.x+20,coordinate.y+25);
        graph.drawImage(Info.getResetButtonImage(),coordinate.x,coordinate.y,width,height,null);
    }


}
