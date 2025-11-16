package studio.rossxrio;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Frame extends JFrame {

    private MouseEvent pressedPos;

    public Action aCloseFrame;
    public KeyStroke ksCloseFrame;

    public Frame(int w, int h) {
        aCloseFrame = new AppActions.CloseFrame(this);
        ksCloseFrame = KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK, false);

        this.setSize(new Dimension(w, h));
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(new Color(32, 32, 32));
        URL url = getClass().getResource("/icon/icon.png");
        assert url != null;
        ImageIcon imageIcon = new ImageIcon(url);
        this.setIconImage(imageIcon.getImage());
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

    public void refresh() {
        this.revalidate();
        this.repaint();
    }

    public void addKeyStrokes(JComponent container) {
        container.getInputMap().put(ksCloseFrame, "aCloseFrame");
        container.getActionMap().put("aCloseFrame", aCloseFrame);
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
