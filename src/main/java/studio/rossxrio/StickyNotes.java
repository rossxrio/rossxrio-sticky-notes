package studio.rossxrio;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.*;

public class StickyNotes extends JFrame {
    String name;
    String content;
    Note note;
    JTextArea ta;
    Action aCloseWindow = new CloseWindow();
    private MouseEvent getPressed;

    public StickyNotes(String name, String content, Note note) {
        KeyStroke closeWindowKS= KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK, false);
        this.name = name;
        this.content = content;
        this.note = note;

        JLabel noteName = new JLabel(name);
        noteName.setForeground(Color.BLACK);
        noteName.setFont(Fonts.getRegularFont(Font.PLAIN, 15));
        noteName.setHorizontalAlignment(SwingConstants.CENTER);
        noteName.setVerticalAlignment(SwingConstants.CENTER);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(200, 200, 200));
        topPanel.setPreferredSize(new Dimension(0, 30));
        topPanel.add(noteName, BorderLayout.NORTH);

        ta = new JTextArea();
        ta.setText(content);
        ta.setForeground(Color.WHITE);
        ta.setFont(Fonts.getRegularFont(Font.PLAIN, 20));
        ta.setBackground(new Color(51, 51, 51));
        topPanel.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("a");
                getPressed = e;
            }
        });

        topPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                updatePos(e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        ta.getInputMap().put(closeWindowKS, "aCloseWindow");
        ta.getActionMap().put("aCloseWindow", aCloseWindow);

        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(ta, BorderLayout.CENTER);
        this.setSize(new Dimension(300, 400));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
        this.setLocationRelativeTo(this.getParent());
        this.setVisible(true);
    }

    private void updatePos(int x, int y) {
        int a = this.getX() - getPressed.getX() + x;
        int b = this.getY() - getPressed.getY() + y;
        this.setLocation(a, b);
    }

    private void onClose() {
        try {
            if (!ta.getText().isBlank()) {
                String formattedContent = "{"+ ta.getText().replaceAll("[{}]", "") + "},";
                note.setStickyNoteContent(formattedContent);
                note.setTitle(formattedContent.split("\n")[0].substring(1));
            } else {
                note.setStickyNoteContent("{}");
                note.setTitle("new");
            }

        } catch (Exception ex) {
            System.err.println(ex);
        }
        this.dispose();
    }

    public class CloseWindow extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            onClose();
            note.getParent().requestFocus();
        }
    }
}
