package studio.rossxrio;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Frame extends JFrame {

    private MouseEvent pressedPos;

    public Frame(int w, int h) {
        this.setSize(new Dimension(w, h));
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(new Color(32, 32, 32));
    }

    public int[] getStartPos() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - this.getWidth();
        int y = (int) rect.getMaxY() - this.getHeight();

        return new int[]{x, y};
    }

    public void updatePos(int x, int y) {
        int a = this.getX() - pressedPos.getX() + x;
        int b = this.getY() - pressedPos.getY() + y;
        this.setLocation(a, b);
    }

    public class DraggableWindowZone implements MouseMotionListener, MouseInputListener {
        @Override
        public void mouseDragged(MouseEvent e) {
            updatePos(e.getX(), e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            pressedPos = e;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }



        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }
}
