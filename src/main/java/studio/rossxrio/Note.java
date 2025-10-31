package studio.rossxrio;

import javax.swing.*;
import java.awt.*;

public class Note extends JPanel {
    private String stickyNoteContent;
    private JLabel noteName;
    private String noteTitle;

    public Note(String name, String stickyNoteContent, int w, int h) {
        this.stickyNoteContent = stickyNoteContent;
        noteTitle = name;

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
        openStickyNoteButton.addActionListener(_ -> openStickyNote());

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

    public void setStickyNoteContent(String stickyNoteContent) {
        this.stickyNoteContent = stickyNoteContent;
    }

    public String getStickyNoteContent() {
        return stickyNoteContent;
    }

    private void deleteNote() {
        Window.NOTES.remove(this);
        Container parent = this.getParent();
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
    }

    public void openStickyNote() {
        if (!stickyNoteContent.isBlank()) {
            new StickyNotes(noteTitle, stickyNoteContent.substring(1, stickyNoteContent.length()-2), this);
        } else new StickyNotes(noteTitle, stickyNoteContent, this);
    }
}
