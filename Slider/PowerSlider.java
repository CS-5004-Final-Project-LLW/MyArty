package Slider;

import Coordinate.CoordinateInt;
import Main.Info;

public class PowerSlider extends Slider {

    public PowerSlider(CoordinateInt coordinate, int width, int height) {
        super(coordinate, width, height);
        words = "Power";
        percentage = 0.5;
    }

    @Override
    public boolean update() {
        if (!super.update()) {
            return false;
        }

        Info.powerValue = (int) (100.0 * percentage);
        return true;

    }
}
