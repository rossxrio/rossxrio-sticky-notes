package studio.rossxrio;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class LoadData {
    public static void loadData(JPanel panel, ArrayList<Note> notes) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("./src/main/resources/NOTES/.save.txt"));
        StringBuilder sb = new StringBuilder();
        String string;
        while ((string = br.readLine()) != null) {
            sb.append(string).append('\n');
        }

        int n=0;

        if (sb.isEmpty()) return;
        String[] records = sb.toString().split("\n");
        for (String record : records) {
            System.out.println(record.length());
            Note note = new Note(record.substring(0, Math.min(record.length(), 15)), record, 320, 40);
            notes.add(note);
            panel.add(note);
            n++;
        }

        panel.setLayout(new GridLayout(Math.max(n, 10), 1, 5, 5));

    }
}
