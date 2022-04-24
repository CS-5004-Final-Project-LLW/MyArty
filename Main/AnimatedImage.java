package Main;

import java.awt.image.BufferedImage;

/**
 * A class for storing animated images (for example, GIF)
 */
class AnimatedImage {
    private BufferedImage[] images;
    private int counter = -1;
    private int slowFactor = 1;

    public AnimatedImage(BufferedImage[] images, int rate) {
        this.images = images;
        this.slowFactor = rate;
    }

    private void addCounter() {
        counter++;
        if (counter >= images.length) {
            counter = 0;
        }
    }

    public BufferedImage getFrame() {
        addCounter();
        return images[counter % slowFactor];
    }

}
