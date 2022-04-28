package Button;

import Coordinate.CoordinateAndSize;
import Coordinate.CoordinateInt;
import Main.Info;
import Main.Tools;
import java.awt.Graphics2D;

public class RestartButton extends Button {

    public RestartButton(CoordinateInt coordinate, int width, int height, String text) {
        super(coordinate, width, height, text);
    }

    @Override
    public boolean update() {
        if (isClicked()) {
            Info.restart.set();
        }
        return true;
    }

    @Override
    public void draw(Graphics2D graph) {
        final double scaleFactor = 1.1;
        if (isMouseHovered()) {
            var scaledCAS = Tools.scaledImage(scaleFactor,
                    new CoordinateAndSize(getX(), getY(), width, height));

            graph.drawImage(Info.resetButtonImage.get(), scaledCAS.x, scaledCAS.y, scaledCAS.width,
                    scaledCAS.height, null);
        } else {
            graph.drawImage(Info.resetButtonImage.get(), getX(), getY(), width, height, null);
        }
    }


}
