package studio.rossxrio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AppFrame extends Frame {
    private JLabel appName;
    private JButton addNoteButton;
    private NoteFont noteFont;

    private JPanel wrapTop;
    private JPanel mainContentContainer;
    private JScrollPane jScrollPane;


    private Action aCreateNewNote;
    private Action aDeleteLastNote;
    private Action aOpenLastNote;

    private KeyStroke ksCreateNewNote;
    private KeyStroke ksDeleteLastNote;
    private KeyStroke ksOpenLastNote;

    private final int[] startPos;

    public AppFrame() {
        super(350, 650);
        noteFont = new NoteFont();

        appName = new JLabel("Sticky NOTES", SwingConstants.LEFT);
        setAppName();

        addNoteButton = new JButton("+");
        setAddNoteButton();

        wrapTop = setWrapTop();
        wrapTop.add(appName);
        wrapTop.add(addNoteButton);

        mainContentContainer = new JPanel();
        setMainContentContainer();

        jScrollPane = new JScrollPane(mainContentContainer);
        setScrollPane();
        addNotes();

        aCreateNewNote = new AppActions.CreateNewNote(mainContentContainer);
        aDeleteLastNote = new AppActions.DeleteLastNote(mainContentContainer);
        aOpenLastNote = new AppActions.OpenLastNote(mainContentContainer);

        ksCreateNewNote = KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK, false);
        ksDeleteLastNote = KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK, false);
        ksOpenLastNote = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK, false);

        addKeyStrokes(wrapTop);
        addKeyStrokes(mainContentContainer);
        addKeyStrokes(jScrollPane);

        startPos = getStartPos();

        this.add(wrapTop, BorderLayout.NORTH);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setLocation(startPos[0], startPos[1]);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setVisible(true);
    }

    private void setAppName() {
        appName.setForeground(Color.WHITE);
        appName.setFont(noteFont.getFont(Font.BOLD, 25));
    }

    private JPanel setWrapTop() {
        JPanel wrapTop = new JPanel();
        wrapTop.setLayout(new FlowLayout());
        wrapTop.setBackground(new Color(32, 32, 32));
        wrapTop.addMouseMotionListener(new DraggableWindowZone());
        wrapTop.addMouseListener(new DraggableWindowZone());
        wrapTop.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (Character.isDigit(e.getKeyChar())) {
                    int n = Character.getNumericValue(e.getKeyChar());
                    if (n <= mainContentContainer.getComponents().length) {
                        Note note = (Note) mainContentContainer.getComponent(n - 1);
                        note.openStickyNote();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        return wrapTop;
    }

    private void setAddNoteButton() {
        addNoteButton.setBackground(new Color(32, 32, 32));
        addNoteButton.setFont(noteFont.getFont(Font.BOLD, 30));
        addNoteButton.setForeground(Color.WHITE);
        addNoteButton.setBorder(null);
        addNoteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addNoteButton.addActionListener(_ -> addNote());
    }

    private void addNote() {
        mainContentContainer.add(new Note(new Data("new")));
        updateGrid();
    }

    public void addNotes() {
        for (Data d : DataMgmt.getDataIndex()) {
            mainContentContainer.add(new Note(d));
        }
        updateGrid();
    }

    private void setMainContentContainer() {
        mainContentContainer.setBackground(new Color(32, 32, 32));
        mainContentContainer.setLayout(new GridLayout(10, 1, 5, 5));
    }

    private void updateGrid() {
        mainContentContainer.setLayout(new GridLayout(Math.max(10, mainContentContainer.getComponents().length), 1, 10, 10));
        refresh();
    }

    private void setScrollPane() {
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane.setBorder(null);
    }

    @Override
    public void addKeyStrokes(JComponent container) {
        super.addKeyStrokes(container);
        container.getInputMap().put(ksCreateNewNote, "aCreateNewNote");
        container.getActionMap().put("aCreateNewNote", aCreateNewNote);

        container.getInputMap().put(ksDeleteLastNote, "aDeleteLastNote");
        container.getActionMap().put("aDeleteLastNote", aDeleteLastNote);

        container.getInputMap().put(ksOpenLastNote, "aOpenLastNote");
        container.getActionMap().put("aOpenLastNote", aOpenLastNote);
    }
}
