package studio.rossxrio;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
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
    private Note context;

    public StickyNotes(Data data, Note context) {
        super(300, 400);
        this.data = data;
        this.context = context;

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
                if (!data.getName().equalsIgnoreCase("new")) {
                    DataMgmt.DATA_INDEX.add(data);
                    DataMgmt.updateDataObjects();
                    DataMgmt.saveData(StickyNotes.this.data, ta.getText());
                }
                dispose();
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

        ta.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onUpdateText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onUpdateText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onUpdateText();
            }
        });
    }

    private void onUpdateText() {
        try {
            String[] name = ta.getDocument().getText(0, Math.min(ta.getDocument().getLength(), 15)).split("\n", 2);
            data.setName((name[0].isBlank() ? "unnamed" : name[0]));
            setNoteName();
            context.setData(data);
            context.setNoteLabel();
            refresh();
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }
}
