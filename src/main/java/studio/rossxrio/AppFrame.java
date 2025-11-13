package studio.rossxrio;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends Frame {
    private JLabel appName;
    private JButton addNoteButton;
    private NoteFont noteFont;
    private JPanel wrapTop;

    private JPanel mainContentContainer;
    private JScrollPane jScrollPane;

//    private Action aCloseWindow;
//    private Action aNewNote;
//    private Action aDelLastNote;
//    private Action aOpenLastNote;

//    private KeyStroke ksCloseWindow;
//    private KeyStroke ksNewNote;
//    private KeyStroke ksDelLastNote;
//    private KeyStroke ksOpenLastNote;
    private final int[] startPos;

    public AppFrame() {
        super(350, 650);
        noteFont = new NoteFont();
//        aCloseWindow = new AppFrame.CloseWindow();
//        aNewNote = new AppFrame.NewNote();
//        aDelLastNote = new AppFrame.DelLastNote();
//        aOpenLastNote = new AppFrame.OpenLastNote();

//        ksCloseWindow = KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK, false);
//        ksNewNote = KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK, false);
//        ksDelLastNote = KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK, false);
//        ksOpenLastNote = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK, false);

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

//        addKeyStrokes(wrapTop);
//        addKeyStrokes(mainContentContainer);
//        addKeyStrokes(jScrollPane);

        startPos = getStartPos();

        this.add(wrapTop, BorderLayout.NORTH);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setLocation(startPos[0], startPos[1]);
        this.setResizable(false);
        this.setUndecorated(false);
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
        mainContentContainer.setLayout(new GridLayout(1 + Math.max(9, DataMgmt.getDataIndex().size()), 1, 5, 5));
        refresh();
    }

    private void setScrollPane() {
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane.setBorder(null);
    }

//    private void addKeyStrokes(JComponent container) {
//        container.getInputMap().put(ksCloseWindow, "aCloseWindow");
//        container.getActionMap().put("aCloseWindow", aCloseWindow);
//        container.getInputMap().put(ksNewNote, "aNewNote");
//        container.getActionMap().put("aNewNote", aNewNote);
//        container.getInputMap().put(ksDelLastNote, "aDelLastNote");
//        container.getActionMap().put("aDelLastNote", aDelLastNote);
//        container.getInputMap().put(ksOpenLastNote, "aOpenLastNote");
//        container.getActionMap().put("aOpenLastNote", aOpenLastNote);
//    }
}
