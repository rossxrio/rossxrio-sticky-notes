package studio.rossxrio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StickyNotes extends JFrame {
    public StickyNotes(String name, String content, Note note) {

        this.setTitle(name);
        TextArea ta = new TextArea(content,0, 0, TextArea.SCROLLBARS_NONE);
        ta.setForeground(Color.WHITE);
        ta.setFont(Fonts.getRegularFont(Font.PLAIN, 15));
        ta.setBackground(new Color(51, 51, 51));

        this.add(ta);
        this.setSize(new Dimension(300, 350));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(this.getParent());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if(ta.getText().isBlank()) {
                        note.setTitle("new");
                        note.setStickyNoteContent("Any TO-DO's?");
                    } else {
                        StringBuilder sb = new StringBuilder(ta.getText());
                        String title = sb.substring(0, Math.min(ta.getText().length(), 15));
                        note.setTitle(title);
                        note.setStickyNoteContent(sb.toString());
                    }
                } catch (Exception ex) {
                    System.err.println(ex);
                }
            }
        });
        this.setVisible(true);
    }
}
