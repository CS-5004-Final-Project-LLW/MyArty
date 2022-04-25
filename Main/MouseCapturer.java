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
        Info.setClicking(true);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Info.setDragging(true);
        Info.setCursorX(e.getX());
        Info.setCursorY(e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Info.setCursorX(e.getX());
        Info.setCursorY(e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        Info.setPressed(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Info.setDragging(false);
        Info.setPressed(false);
    }
}
