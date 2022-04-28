package Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Mouse Capturer class refractoring from `GUI`
 */
class MouseCapturer implements MouseListener, MouseMotionListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        Info.clicking.set(true);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Info.dragging.set(true);
        Info.cursorX.set(e.getX());
        Info.cursorY.set(e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Info.cursorX.set(e.getX());
        Info.cursorY.set(e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        Info.pressed.set(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Info.dragging.set(false);
        Info.pressed.set(false);
    }
}
