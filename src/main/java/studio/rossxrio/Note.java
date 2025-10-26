package studio.rossxrio;

import javax.swing.*;
import java.awt.*;

public class Note extends JPanel {
    private StickyNotes stickyNotes;
    private String stickyNoteContent;
    private JLabel noteName;

    public Note(String name, String content, int w, int h) {
        stickyNoteContent = content;

        noteName = new JLabel(name);
        noteName.setForeground(Color.WHITE);
        noteName.setFont(Fonts.getRegularFont(Font.PLAIN, 15));

        JButton deleteNoteButton = new JButton("X");
        deleteNoteButton.setBackground(new Color(51, 51, 51));
        deleteNoteButton.setFont(Fonts.getRegularFont(Font.PLAIN, 15));
        deleteNoteButton.setForeground(Color.WHITE);
        deleteNoteButton.setBorder(null);
        deleteNoteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteNoteButton.addActionListener(l -> deleteNote());

        JButton openStickyNoteButton = new JButton("->");
        openStickyNoteButton.setBackground(new Color(51, 51, 51));
        openStickyNoteButton.setFont(Fonts.getRegularFont(Font.PLAIN, 15));
        openStickyNoteButton.setForeground(Color.WHITE);
        openStickyNoteButton.setBorder(null);
        openStickyNoteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        openStickyNoteButton.addActionListener(l -> openStickyNote(name, content));

        JPanel wrapButtons = new JPanel();
        wrapButtons.setBackground(new Color(51, 51, 51));
        wrapButtons.setLayout(new FlowLayout());
        wrapButtons.add(deleteNoteButton);
        wrapButtons.add(openStickyNoteButton);

        JPanel wrapTop = new JPanel();
        wrapTop.setLayout(new BorderLayout());
        wrapTop.setBackground(new Color(51, 51, 51));

        wrapTop.add(noteName, BorderLayout.CENTER);
        wrapTop.add(wrapButtons, BorderLayout.EAST);

        this.setLayout(new BorderLayout());
        this.add(wrapTop, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(w, h));
        this.setBackground(new Color(51, 51, 51));
    }

    public void setTitle(String name) {
        noteName.setText(name);
    }

    public void setStickyNoteContent(String content) {
        stickyNoteContent = content;
    }


    public String getStickyNoteContent() {
        return stickyNoteContent;
    }

    public JLabel getNoteName() {
        return noteName;
    }

    private void deleteNote() {
        Window.NOTES.remove(this);
        Container parent = this.getParent();
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
    }

    private void openStickyNote(String name, String content) {
        stickyNotes = new StickyNotes(name, content, this);
    }
}
