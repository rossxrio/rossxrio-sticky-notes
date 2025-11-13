package studio.rossxrio;

import javax.swing.*;
import java.awt.*;

public class Note extends JPanel {
    private Data data;

    private JLabel noteLabel;
    private NoteFont noteFont;

    private JButton openStickyNoteButton;
    private JButton deleteNoteButton;
    private JPanel wrapButtons;

    private JPanel wrapElements;

    public Note(Data data) {
        this.data = data;

        noteFont = new NoteFont();

        noteLabel = new JLabel();
        setNoteLabel();
        setNoteName();

        openStickyNoteButton = new JButton("->");
        openStickyNoteButton();

        deleteNoteButton = new JButton("X");
        setDeleteNoteButton();

        wrapButtons = new JPanel();
        setWrapButtons();

        wrapElements = new JPanel();
        setWrapElements();

        wrapElements.add(noteLabel, BorderLayout.CENTER);
        wrapElements.add(wrapButtons, BorderLayout.EAST);

        this.setLayout(new BorderLayout());
        this.add(wrapElements, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(320, 40));
        this.setBackground(new Color(51, 51, 51));
    }

    public void setNoteLabel() {
        noteLabel.setForeground(Color.WHITE);
        noteLabel.setFont(noteFont.getFont(Font.PLAIN, 15));

    }

    public void setNoteName() {
        noteLabel.setText(data.getName());
    }

    public void setData(Data data) {
        this.data = data;
    }

    private void openStickyNoteButton() {
        openStickyNoteButton.setBackground(new Color(51, 51, 51));
        openStickyNoteButton.setFont(noteFont.getFont(java.awt.Font.PLAIN, 15));
        openStickyNoteButton.setForeground(Color.WHITE);
        openStickyNoteButton.setBorder(null);
        openStickyNoteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        openStickyNoteButton.addActionListener(_ -> openStickyNote());
    }

    private void openStickyNote() {
        new StickyNotes(data, this);
    }

    private void setDeleteNoteButton() {
        deleteNoteButton.setBackground(new Color(51, 51, 51));
        deleteNoteButton.setFont(noteFont.getFont(java.awt.Font.PLAIN, 15));
        deleteNoteButton.setForeground(Color.WHITE);
        deleteNoteButton.setBorder(null);
        deleteNoteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteNoteButton.addActionListener(l -> deleteNote());
    }

    private void deleteNote() {
        DataMgmt.deleteDataObject(data);
        DataMgmt.updateDataFile();

        Container parent = this.getParent();
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
    }

    private void setWrapButtons() {
        wrapButtons.setBackground(new Color(51, 51, 51));
        wrapButtons.setLayout(new FlowLayout());
        wrapButtons.add(deleteNoteButton);
        wrapButtons.add(openStickyNoteButton);
    }

    private void setWrapElements() {
        wrapElements.setLayout(new BorderLayout());
        wrapElements.setBackground(new Color(51, 51, 51));
    }
}
