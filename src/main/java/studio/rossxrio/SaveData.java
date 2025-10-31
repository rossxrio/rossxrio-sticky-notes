package studio.rossxrio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class SaveData {
    public static void saveData(ArrayList<Note> notes) throws Exception {
        // TODO Rework
        BufferedWriter bw = new BufferedWriter(new FileWriter("./src/main/resources/NOTES/.save.txt"));
        StringBuilder notesContent = new StringBuilder();
        for (Note n : notes) {
            notesContent.append(n.getStickyNoteContent()).append('\n');

        }
        bw.write(notesContent.toString());
        bw.flush();
        bw.close();
    }
}
