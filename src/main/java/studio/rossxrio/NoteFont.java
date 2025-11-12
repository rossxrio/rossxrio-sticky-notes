package studio.rossxrio;

import java.awt.*;
import java.io.InputStream;

public class NoteFont {
    private Font font;
    public NoteFont() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("fonts/0xProtoNerdFont-Regular.ttf");
        try {
            font = Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public Font getFont(int style, int size) {
        return font.deriveFont(style, size);
    }
}
