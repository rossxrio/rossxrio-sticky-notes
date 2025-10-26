package studio.rossxrio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class SaveData {
    public static void saveData(ArrayList<Note> notes) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter("./src/main/resources/NOTES/.save.txt"));
        String noteData;
        for (Note n : notes) {
            noteData = n.getStickyNoteContent() + "\n";
            bw.write(noteData);
            bw.flush();
        }
    }
}
