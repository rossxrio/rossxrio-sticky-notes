package studio.rossxrio;

import java.awt.*;
import java.io.InputStream;

public class NoteFont {
    private Font font;
    public NoteFont() {
        InputStream is = NoteFont.class.getResourceAsStream("/fonts/0xProtoNerdFont-Regular.ttf");
        try {
            assert is != null;
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            System.err.println(e);
            font = new Font("arial", Font.BOLD, 20);
        }
    }

    public Font getFont(int style, int size) {
        return font.deriveFont(style, size);
    }
}
