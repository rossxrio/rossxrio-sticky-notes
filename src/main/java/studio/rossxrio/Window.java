package studio.rossxrio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Window extends JFrame {
    JPanel mainContentContainer;
    public static final ArrayList<Note> NOTES = new ArrayList<>();

    private int page = 1;
    private int nNotes = NOTES.size();
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

        JPanel wrapTop = new JPanel();
        wrapTop.setLayout(new FlowLayout());
        wrapTop.setBackground(new Color(32, 32, 32));

        wrapTop.add(appName);
        wrapTop.add(addNoteButton);

        mainContentContainer = new JPanel();
        mainContentContainer.setBackground(new Color(32, 32, 32));
        mainContentContainer.setLayout(new GridLayout(page*10, 1, 5, 5));

        JScrollPane jScrollPane = new JScrollPane(mainContentContainer);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane.setBorder(null);


        this.setSize(new Dimension(350, 650));
        this.getContentPane().setLayout(new BorderLayout());
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(new Color(32, 32, 32));


        this.add(wrapTop, BorderLayout.NORTH);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    SaveData.saveData(NOTES);
                } catch (Exception ex) {
                    System.err.println(ex);
                }
            }
        });
        loadData();
        this.setVisible(true);
    }

    private void addNote() {
        NOTES.add(new Note("new", "", 320, 40));
        mainContentContainer.add(NOTES.getLast());
        nNotes++;
        if (nNotes == 9) {
            page++;
            mainContentContainer.setLayout(new GridLayout(page*10, 1, 5, 5));
        }
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
}
