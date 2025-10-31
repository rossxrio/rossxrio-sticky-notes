package studio.rossxrio;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Window extends JFrame {
    JPanel mainContentContainer;
    public static final ArrayList<Note> NOTES = new ArrayList<>();
    private MouseEvent getPressed;

    Action aCloseWindow = new Window.CloseWindow();
    Action aNewNote = new Window.NewNote();
    Action aDelLastNote = new Window.DelLastNote();
    Action aOpenLastNote = new Window.OpenLastNote();

    KeyStroke ksCloseWindow = KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK, false);
    KeyStroke ksNewNote = KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK, false);
    KeyStroke ksDelLastNote = KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK, false);
    KeyStroke ksOpenLastNote = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK, false);

    public Window() {

        JLabel appName = new JLabel("Sticky NOTES", SwingConstants.LEFT);
        appName.setForeground(Color.WHITE);
        appName.setFont(Fonts.getRegularFont(Font.PLAIN, 25));

        JButton addNoteButton = new JButton("+");
        addNoteButton.setBackground(new Color(32, 32, 32));
        addNoteButton.setFont(Fonts.getRegularFont(Font.PLAIN, 30));
        addNoteButton.setForeground(Color.WHITE);
        addNoteButton.setBorder(null);
        addNoteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addNoteButton.addActionListener(_ -> addNote());

        JPanel wrapTop = getWrapTop();
        wrapTop.add(appName);
        wrapTop.add(addNoteButton);

        wrapTop.getInputMap().put(ksCloseWindow, "aCloseWindow");
        wrapTop.getActionMap().put("aCloseWindow", aCloseWindow);
        wrapTop.getInputMap().put(ksNewNote, "aNewNote");
        wrapTop.getActionMap().put("aNewNote", aNewNote);
        wrapTop.getInputMap().put(ksDelLastNote, "aDelLastNote");
        wrapTop.getActionMap().put("aDelLastNote", aDelLastNote);
        wrapTop.getInputMap().put(ksOpenLastNote, "aOpenLastNote");
        wrapTop.getActionMap().put("aOpenLastNote", aOpenLastNote);

        mainContentContainer = new JPanel();
        mainContentContainer.setBackground(new Color(32, 32, 32));
        mainContentContainer.setLayout(new GridLayout(10, 1, 5, 5));

        mainContentContainer.getInputMap().put(ksCloseWindow, "aCloseWindow");
        mainContentContainer.getActionMap().put("aCloseWindow", aCloseWindow);
        mainContentContainer.getInputMap().put(ksNewNote, "aNewNote");
        mainContentContainer.getActionMap().put("aNewNote", aNewNote);
        mainContentContainer.getInputMap().put(ksDelLastNote, "aDelLastNote");
        mainContentContainer.getActionMap().put("aDelLastNote", aDelLastNote);
        mainContentContainer.getInputMap().put(ksOpenLastNote, "aOpenLastNote");
        mainContentContainer.getActionMap().put("aOpenLastNote", aOpenLastNote);

        JScrollPane jScrollPane = new JScrollPane(mainContentContainer);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane.setBorder(null);
        jScrollPane.getInputMap().put(ksCloseWindow, "aCloseWindow");
        jScrollPane.getActionMap().put("aCloseWindow", aCloseWindow);
        jScrollPane.getInputMap().put(ksNewNote, "aNewNote");
        jScrollPane.getActionMap().put("aNewNote", aNewNote);
        jScrollPane.getInputMap().put(ksDelLastNote, "aDelLastNote");
        jScrollPane.getActionMap().put("aDelLastNote", aDelLastNote);
        jScrollPane.getInputMap().put(ksOpenLastNote, "aOpenLastNote");
        jScrollPane.getActionMap().put("aOpenLastNote", aOpenLastNote);


        this.setSize(new Dimension(350, 650));
        this.setUndecorated(true);
        this.getContentPane().setLayout(new BorderLayout());
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(new Color(32, 32, 32));
        this.add(wrapTop, BorderLayout.NORTH);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - this.getWidth();
        int y = (int) rect.getMaxY() - this.getHeight();
        this.setLocation(x, y);
        this.setVisible(true);
        loadData();
        this.setVisible(true);
    }

    private JPanel getWrapTop() {
        JPanel wrapTop = new JPanel();
        wrapTop.setLayout(new FlowLayout());
        wrapTop.setBackground(new Color(32, 32, 32));
        wrapTop.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("a");
                getPressed = e;
            }
        });

        wrapTop.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                updatePos(e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        return wrapTop;
    }

    private void addNote() {
        NOTES.add(new Note("new", "", 320, 40));
        mainContentContainer.add(NOTES.getLast());
        updateGrid();
    }

    private void updateGrid() {
        mainContentContainer.setLayout(new GridLayout(1 + Math.max(9, NOTES.size()), 1, 5, 5));
        refresh();
    }

    private void loadData() {
        try {
            LoadData.loadData(mainContentContainer, NOTES);
            refresh();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void refresh() {
        this.revalidate();
        this.repaint();
    }

    private void updatePos(int x, int y) {
        int a = this.getX() - getPressed.getX() + x;
        int b = this.getY() - getPressed.getY() + y;
        this.setLocation(a, b);
    }

    private void onClose() {
        try {
            SaveData.saveData(NOTES);
        } catch (Exception ex) {
            System.err.println(ex);
        }
        dispose();
    }

    public class CloseWindow extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            onClose();
        }
    }

    public class NewNote extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            addNote();
        }
    }

    public class DelLastNote extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int n = mainContentContainer.getComponentCount() - 1;
                if (n < 0) return;
                Component note = mainContentContainer.getComponent(n);
                if (note instanceof Note) {
                    note.getParent().remove(note);
                    NOTES.removeLast();
                    updateGrid();
                }
            } catch (Exception ignore) {
            }
        }
    }

    public class OpenLastNote extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int n = mainContentContainer.getComponentCount() - 1;
                if (n < 0) return;
                Component note = mainContentContainer.getComponent(n);
                if (note instanceof Note) {
                    ((Note) note).openStickyNote();
                }
            } catch (Exception ignore) {
            }
        }
    }

}
