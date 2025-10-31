package studio.rossxrio;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoadData {
    public static void loadData(JPanel panel, ArrayList<Note> notes) throws Exception {
        // TODO Rework
        BufferedReader br = new BufferedReader(new FileReader("./src/main/resources/NOTES/.save.txt"));
        StringBuilder gatherNotes = new StringBuilder();
        String str;
        while ((str = br.readLine()) != null) {
            gatherNotes.append(str).append('\n');
        }
        if (gatherNotes.isEmpty()) return;

        Pattern pattern = Pattern.compile("(\\{[^\\}]*\\})");
        Matcher matcher = pattern.matcher(gatherNotes);
        ArrayList<String> noteData = new ArrayList<>();

        while (matcher.find()) {
            noteData.add(matcher.group(1).trim());
        }

        for (String s : noteData) {
            if (!s.isBlank()) {
                Note note = new Note(s.substring(1, Math.min(s.length(), 10)-1).trim(), s+",", 320, 40);
                notes.add(note);
                panel.add(note);
            }
        }
    }
}
