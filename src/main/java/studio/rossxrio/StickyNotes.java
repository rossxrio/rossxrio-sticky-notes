package studio.rossxrio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StickyNotes extends Frame {
    private JLabel noteName;
    private NoteFont noteFont;
    private JPanel topPanel;
    private JTextArea ta;
    private JScrollPane jScrollPane;

    private Data data;

    public StickyNotes(Data data) {
        super(300, 400);
        this.data = data;
        this.data.setName("TEST");

        noteFont = new NoteFont();

        noteName = new JLabel();
        setNoteName();

        topPanel = new JPanel();
        setTopPanel();

        ta = new JTextArea();
        setTa();

        jScrollPane = new JScrollPane(ta);
        jScrollPane.setBorder(null);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        this.setLayout(new BorderLayout());
        this.setAlwaysOnTop(true);
        this.setUndecorated(false);
        this.setLocationRelativeTo(this.getParent());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("closing...");
                System.out.println(StickyNotes.this.data.getPath());
                DataMgmt.saveData(StickyNotes.this.data, ta.getText());
                DataMgmt.updateDataObjects(StickyNotes.this.data);
            }
        });
        this.setVisible(true);
    }

    private void setNoteName() {
        noteName.setText(data.getName());
        noteName.setForeground(Color.BLACK);
        noteName.setFont(noteFont.getFont(Font.BOLD, 15));
        noteName.setHorizontalAlignment(SwingConstants.CENTER);
        noteName.setVerticalAlignment(SwingConstants.CENTER);
    }

    private void setTopPanel() {
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(200, 200, 200));
        topPanel.setPreferredSize(new Dimension(0, 30));
        topPanel.add(noteName, BorderLayout.NORTH);
    }

    private void setTa() {
        ta.setForeground(Color.WHITE);
        ta.setBackground(new Color(51, 51, 51));
        ta.setCaretColor(Color.WHITE);
        ta.setFont(noteFont.getFont(Font.BOLD, 20));
        ta.setLineWrap(true);

        ta.setText(DataMgmt.loadData(data));
    }


    public static void main(String[] args) {
        new StickyNotes(new Data("new"));
    }
}
