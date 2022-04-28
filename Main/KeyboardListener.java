package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        Info.keyPressed.set(true);
        Info.keyReleased.set(false);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        Info.keyReleased.set(true);
        Info.keyPressed.set(false);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        Info.keyEntered.set(true);

    }

}
